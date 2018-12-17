package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.DateUtils;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.dao.WbOrderFreeDao;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.WbOrderFreeEntity;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.operation.service.WbOrderFreeService;
import leiren.haozhaojob.modules.operation.service.WbUserService;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("tWbOrderFreeService")
public class WbOrderFreeServiceImpl extends ServiceImpl<WbOrderFreeDao, WbOrderFreeEntity> implements WbOrderFreeService {
    @Autowired
    WbUserService wbUserService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    private SysUserService sysUserService;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils selectTWBOrderFreeBySearchCondition(Map<String, Object> params) {
        //获取查询过滤条件参数(商户id) d
        String num = (String)params.get(Constant.SQL_FILTER);
        String merchantIdSearch = null;
        List<String> merchantIds = null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs= num.split(",");
            merchantIds=Arrays.asList(strs);
            //构造条件查询过滤参数字符串 d
            merchantIdSearch = StringUtils.join(merchantIds, ",");
        }
        //获取查询起始时间 d
        String startTime = (String)params.get("startTime");
        //声明查询起始时间参数字符串 d
        Date startTimeSearch = DateUtils.searchStartDateDeal(startTime);
        //获取查询终止时间 d
        String endTime = (String)params.get("endTime");
        //声明查询结束时间参数字符串 d
        Date endTimeSearch = DateUtils.searchEndDateDeal(endTime);
        //获取用户名称 && 用户电话号码 d
        String userName = (String)params.get("userName");
        //根据用户名称或用户电话号码模糊查询user_id d
        List<Integer> userIdList = wbUserService.selectUserIdByUserNameOrMobile(userName);
        String userIds = null;
        if (!CollectionUtils.isEmpty(userIdList)){
            userIds = StringUtils.join(userIdList, ",");
        }
        //获取商户名称 d
        String merchantName = (String)params.get("merchantName");
        //根据商户名称模糊查询商户id d
        List<Integer> merchantIdList = merchantService.selectMerchantIdByMerchantName(merchantName);
        String merchantId = null;
        if (!CollectionUtils.isEmpty(merchantIdList)) {
            merchantId = StringUtils.join(merchantIdList, ",");
        }
        //获取订单号 d
        String orderId = (String)params.get("orderId");
        Page<WbOrderFreeEntity> page = this.selectPage(
                new Query<WbOrderFreeEntity>(params).getPage(),
                new EntityWrapper<WbOrderFreeEntity>()
                        .in(params.get(Constant.SQL_FILTER) != null, "merchant_id" , merchantIdSearch)
                        .eq("order_type", 5)
                        .between("create_time", startTimeSearch, endTimeSearch)
                        .in(StringUtils.isNotBlank(merchantName), "merchant_id", merchantId)
                        .in(StringUtils.isNotBlank(userName), "user_id", userIds)
                        .like(StringUtils.isNotBlank(orderId), "order_id", orderId)
        );
        List<WbOrderFreeEntity> wbOrderFreeEntityList = page.getRecords();
        if (!CollectionUtils.isEmpty(wbOrderFreeEntityList)) {
            for (WbOrderFreeEntity wbOrderFreeEntity : wbOrderFreeEntityList) {
                try {
                    //根据用户id获取用户姓名和电话信息 d
                    WbUserEntity wbUserEntity = wbUserService.selectUserNameAndMobileByUserId(wbOrderFreeEntity.getUserId());
                    if (wbUserEntity != null) {
                        //添加用户名称 d
                        wbOrderFreeEntity.setUserName(wbUserEntity.getRealName());
                        //添加用户电话号码 d
                        wbOrderFreeEntity.setMobile(wbUserEntity.getMobile());
                    }
                    //根据商户id查询商户名称，地址和客户经理信息 d
                    MerchantEntity merchantEntity = merchantService.selectMerchantNameSAdressAndSManById(wbOrderFreeEntity.getMerchantId());
                    if (merchantEntity != null) {
                        //添加商户名称 d
                        wbOrderFreeEntity.setMerchantName(merchantEntity.getMerchantName());
                        //添加免单区域 d
                        wbOrderFreeEntity.setFreeArea(merchantEntity.getSimpleAddress());
                        //根据客户经理id查询客户经理姓名和电话信息 d
                        SysUserEntity sysUserEntity = sysUserService.selectById(merchantEntity.getSalesman());
                        if (sysUserEntity != null) {
                            //添加城市经理 d
                            wbOrderFreeEntity.setSalesMan(sysUserEntity.getRealName());
                            //添加城市经理电话 d
                            wbOrderFreeEntity.setSalesManMobile(sysUserEntity.getMobile());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        page.setRecords(wbOrderFreeEntityList);
        return new PageUtils(page);
    }
}
