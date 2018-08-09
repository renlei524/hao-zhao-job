package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.entity.TUserWeiBaoEntity;
import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import com.scxxwb.net.admin.modules.operation.service.TUserWeiBaoService;
import com.scxxwb.net.admin.modules.operation.service.TWbOrderService;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TWbUserDao;
import com.scxxwb.net.admin.modules.operation.entity.TWbUserEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbUserService;
import org.springframework.transaction.annotation.Transactional;


@Service("tWbUserService")
public class TWbUserServiceImpl extends ServiceImpl<TWbUserDao, TWbUserEntity> implements TWbUserService {
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    private TUserWeiBaoService tUserWeiBaoService;
    @Autowired
    private TWbOrderService tWbOrderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String realName = (String)params.get("realName");
        boolean flag = StringUtils.isNotBlank(realName);
        Page<TWbUserEntity> page = this.selectPage(
                new Query<TWbUserEntity>(params).getPage(),
                new EntityWrapper<TWbUserEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                        .andNew(flag, "real_name LIKE '%" + realName +"%'")
                        .or(flag, "mobile LIKE '%" + realName + "%'")
                        .or(flag, "id_card LIKE '%" + realName + "%'")
        );
        List<TWbUserEntity> tWbUserEntityList = page.getRecords();
        for (TWbUserEntity tWbUserEntity : tWbUserEntityList){
            //获取用户id
            try {
                Integer userId = tWbUserEntity.getId();
                Map<String, Date> map = tWbOrderService.selectTWBUserRecconsumptionTime(userId);
                if (map != null) {
                    //获取最近消费时间
                    Date date = map.get("rec_consumption_time");
                    //添加最近消费时间
                    tWbUserEntity.setRecConsumptionTime(date);
                    //获取消费总金额和最近消费商户id
                    TWbOrderEntity tWbOrderEntity = tWbOrderService.selectTWBUserTotalAndArea(userId);
                    //添加消费总金额
                    tWbUserEntity.setTotalConsumption(tWbOrderEntity.getTotalConsumption() * 1.0 / 100);
                    //获取最近消费商户id
                    Integer merchantId = tWbOrderEntity.getRecConsumptionMerchantId();
                    //根据商户id查询商户信息
                    MerchantEntity merchantEntity = merchantService.selectById(merchantId);
                    if (merchantEntity != null){
                        //获取客户经理id
                        Integer salesManId = merchantEntity.getSalesman();
                        //根据客户经理id查询客户经理信息
                        SysUserEntity sysUserEntity = sysUserService.selectById(salesManId);
                        if(sysUserEntity != null) {
                            //添加城市经理名称
                            tWbUserEntity.setSalesMan(sysUserEntity.getRealName());
                            //添加城市经理电话
                            tWbUserEntity.setSalesManTel(sysUserEntity.getMobile());
                        }
                    //添加最近消费地区名称
                    tWbUserEntity.setRecConsumptionArea(merchantEntity.getAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page.setRecords(tWbUserEntityList);
        return new PageUtils(page);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public List<TWbUserEntity> queryList(Map<String, Object> map) {
        List<TWbUserEntity> userList =
                this.selectList(new EntityWrapper<TWbUserEntity>()
                .addFilterIfNeed(map.get(Constant.SQL_FILTER) != null, (String)map.get(Constant.SQL_FILTER)));
        return  userList;
    }

    @Override
    @Transactional
    public Boolean saveUser(TWbUserEntity tWbUser) {
        boolean result = this.insert(tWbUser);
        if(result){
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", tWbUser.getMobile());
            TWbUserEntity tWbUserEntity = (this.selectByMap(map)).get(0);
            TUserWeiBaoEntity tUserWeiBaoEntity = new TUserWeiBaoEntity();
            tUserWeiBaoEntity.setUserId(tWbUserEntity.getId());
            result = tUserWeiBaoService.insert(tUserWeiBaoEntity);
        }

        return result;
    }

}
