package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.modules.operation.entity.WbCardEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.WbCardDao;
import leiren.haozhaojob.modules.operation.service.WbCardService;


@Service("tWbCardService")
public class WbCardServiceImpl extends ServiceImpl<WbCardDao, WbCardEntity> implements WbCardService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    protected MerchantService merchantService;
    @Autowired
    private WbCardService wbCardService;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list = null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs = num.split(",");
            list=Arrays.asList(strs);
        }
        List<Integer> merchantIds = merchantService.getMerchant(list);
        String merchantId = StringUtils.join(merchantIds,',');
        String cardContent = (String)params.get("cardContent");
        String creatorType = (String)params.get("creatorType");
        String type = (String)params.get("type");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        if (startTime != null){
            startTime = startTime + " 00:00:00";
        }
        if (endTime != null){
            endTime = endTime + " 23:59:59";
        }
        //声明查询过滤参数商户id字符串 d
        String merchantSearchIds = null;
        //获取查询时商户名称参数 d
        String merchantName = (String)params.get("merchantName");
        if (merchantName != null && !merchantName.isEmpty()) {
            //获取商户id集合 d
            List<Integer> merchantIdList = merchantService.selectMerchantIdWhereNameLikeSearch(merchantName);
            //处理查询过滤参数商户id字符串 d
            merchantSearchIds = StringUtils.join(merchantIdList, ",");
        }
        Page<WbCardEntity> page = this.selectPage(
                new Query<WbCardEntity>(params).getPage(),
                new EntityWrapper<WbCardEntity>()
                        .like(StringUtils.isNotBlank(cardContent),"card_content", cardContent)
                        .addFilterIfNeed(StringUtils.isNotBlank(creatorType) && !creatorType.equals("-1"),"creator_type = " + creatorType)
                        .addFilterIfNeed(StringUtils.isNotBlank(type) && !type.equals("-1"),"type = " + type)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "creator_id in ("+merchantId+")")
                        .addFilterIfNeed(StringUtils.isNotBlank(merchantName), "creator_id in (" + merchantSearchIds + ")")
                        .between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime),"create_date",startTime,endTime)
        );
        //获取优惠券集合
        List<WbCardEntity> wbCardEntityList = page.getRecords();
        //加入商户名称
        for (WbCardEntity wbCardEntity : wbCardEntityList) {
            if(wbCardEntity.getCreatorId() == 0) {
                wbCardEntity.setCreatorName("微宝平台");
                continue;
            }
            //根据创建人id获取商户名称
            String merchantNames = merchantService.selectMerchantNameByCreatorId(wbCardEntity.getCreatorId());
            wbCardEntity.setCreatorName(merchantNames);
        }
        return new PageUtils(page);
    }

    @Override
    public void stopWBCard(Integer[] ids) {
        List<WbCardEntity> wbCardEntityList = new ArrayList<>();
        WbCardEntity wbCardEntity = null;
        for (int i = 0; i < ids.length; i ++) {
            wbCardEntity = new WbCardEntity();
            wbCardEntity.setStatus(2);
            wbCardEntity.setId(ids[i]);
            wbCardEntityList.add(wbCardEntity);
        }
        wbCardService.updateBatchById(wbCardEntityList);
    }
}
