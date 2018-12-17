package leiren.haozhaojob.modules.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import leiren.haozhaojob.modules.operation.dao.GoodsCheckDao;
import leiren.haozhaojob.modules.operation.entity.GoodsCheckEntity;
import leiren.haozhaojob.modules.operation.entity.GoodsEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;
import leiren.haozhaojob.modules.operation.service.GoodsCheckService;
import leiren.haozhaojob.modules.operation.service.GoodsService;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("tGoodsCheckService")
public class GoodsCheckServiceImpl extends ServiceImpl<GoodsCheckDao, GoodsCheckEntity> implements GoodsCheckService {

    @Value(value = "${scxxwb.nginx.goodsPath}")
    private String goodsNginxPath;

    @Autowired
    MerchantService merchantService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    MerchantProductCustomerLabelService merchantProductCustomerLabelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String search = (String)params.get("search");
        String status = (String)params.get("status");
        Page<GoodsCheckEntity> page = this.selectPage(
                new Query<GoodsCheckEntity>(params).getPage(),
                new EntityWrapper<GoodsCheckEntity>()
                .addFilterIfNeed(!StringUtils.isNotBlank(status),"status in (1,3)")
                .addFilterIfNeed(StringUtils.isNotBlank(status) && status.equals("0"),"status in (1,3)")
                .addFilterIfNeed(StringUtils.isNotBlank(status) && !status.equals("0"),"status in ("+status+")")
                .addFilterIfNeed(StringUtils.isNotBlank(search),"(goods_name like '%" + search + "%' " +
                        "or merchant_id in (select id from t_merchant where merchant_name like '%"+search+"%'))")
        );

        for(GoodsCheckEntity goodsCheckEntity : page.getRecords()){
            goodsCheckEntity.setOriginalPrice(goodsCheckEntity.getOriginalPrice().divide(new BigDecimal("100")));
            goodsCheckEntity.setMarketPrice(goodsCheckEntity.getMarketPrice().divide(new BigDecimal("100")));
            goodsCheckEntity.setDiscountPrice(goodsCheckEntity.getDiscountPrice().divide(new BigDecimal("100")));
            goodsCheckEntity.setPromotionalPrice(goodsCheckEntity.getPromotionalPrice().divide(new BigDecimal("100")));
            goodsCheckEntity.setPictures(goodsNginxPath + goodsCheckEntity.getPictures());
            MerchantEntity merchantEntity = merchantService.selectById(goodsCheckEntity.getMerchantId());
            if (merchantEntity != null){
                goodsCheckEntity.setMerchantName(merchantEntity.getMerchantName());
            }
            MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity = merchantProductCustomerLabelService.selectById(goodsCheckEntity.getLabelId());
            if (merchantProductCustomerLabelEntity != null){
                goodsCheckEntity.setLabelName(merchantProductCustomerLabelEntity.getLabelName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void goodsCheck(GoodsCheckEntity goodsCheck) {
        goodsCheck.setOriginalPrice(goodsCheck.getOriginalPrice().multiply(new BigDecimal("100")));
        goodsCheck.setMarketPrice(goodsCheck.getMarketPrice().multiply(new BigDecimal("100")));
        goodsCheck.setDiscountPrice(goodsCheck.getDiscountPrice().multiply(new BigDecimal("100")));
        goodsCheck.setPromotionalPrice(goodsCheck.getPromotionalPrice().multiply(new BigDecimal("100")));
        this.updateAllColumnById(goodsCheck);//全部更新
        if (goodsCheck.getStatus().equals(5)){
            String goodsCheckJson = JSONObject.toJSONString(goodsCheck);
            GoodsEntity goodsEntity = JSONObject.toJavaObject(JSONObject.parseObject(goodsCheckJson), GoodsEntity.class);
            goodsService.insertGoods(goodsEntity);
        }
    }

    @Override
    @Transactional
    public void batchOperation(Map<String, Object> params) {
        String remark = (String)params.get("remark");
        String ids = (String)params.get("ids");
        String [] arrAy = ids.split(",");
        List<String> arrId = Arrays.asList(arrAy);
        params.put("arrId",arrId);
        //修改审核表
        baseMapper.batchOperation(params);
        if (remark == null){
            List<GoodsCheckEntity> goodsCheckEntityList = this.selectList(
                    new EntityWrapper<GoodsCheckEntity>()
                            .addFilterIfNeed(StringUtils.isNotBlank(ids),"id in ("+ids+")")
            );
            //商品表添加数据
            for (GoodsCheckEntity goodsCheckEntity : goodsCheckEntityList){
                String goodsCheckJson = JSONObject.toJSONString(goodsCheckEntity);
                GoodsEntity goodsEntity = JSONObject.toJavaObject(JSONObject.parseObject(goodsCheckJson), GoodsEntity.class);
                goodsService.insertGoods(goodsEntity);
            }
        }
    }

}
