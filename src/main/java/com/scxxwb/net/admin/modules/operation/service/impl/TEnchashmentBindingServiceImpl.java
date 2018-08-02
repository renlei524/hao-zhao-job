package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import com.scxxwb.net.admin.modules.sys.entity.SysDictEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TEnchashmentBindingDao;
import com.scxxwb.net.admin.modules.operation.entity.TEnchashmentBindingEntity;
import com.scxxwb.net.admin.modules.operation.service.TEnchashmentBindingService;


@Service("tEnchashmentBindingService")
public class TEnchashmentBindingServiceImpl extends ServiceImpl<TEnchashmentBindingDao, TEnchashmentBindingEntity> implements TEnchashmentBindingService {

    @Autowired
    MerchantService merchantService;

    @Autowired
    SysDictService sysDictService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询参数
        String queryName = (String)params.get("merchantName");
        boolean flag = StringUtils.isNotBlank((String)params.get("merchantName"));
        String merchantId = null;
        if(queryName != null) {
            //根据商户名称查询出商户信息
            List<MerchantEntity> merchantEntityList = merchantService.getMerchantByArea(params);

            //初始化对象
            List<Integer> merchantIds =new ArrayList<>();
            for (MerchantEntity merchantEntity : merchantEntityList){
                merchantIds.add(merchantEntity.getId());
            }
            if (merchantIds.size() == 0){
                merchantIds.add(0);
            }
            merchantId = StringUtils.join(merchantIds,',');
        }
        Page<TEnchashmentBindingEntity> page = this.selectPage(
                new Query<TEnchashmentBindingEntity>(params).getPage(),
                new EntityWrapper<TEnchashmentBindingEntity>()
                        .or(flag, "account LIKE '%" + queryName + "%'")
                        .or(flag, "subbranch LIKE '%" + queryName + "%'")
                        .or(flag, "account_name LIKE '%" + queryName + "%'")
                        .or(flag, "object_id IN (" + merchantId + ")")
        );
        //添加商户名称及开户行名称
        for (TEnchashmentBindingEntity tEnchashmentBindingEntity : page.getRecords()){
            MerchantEntity merchantEntity =merchantService.selectById(tEnchashmentBindingEntity.getObjectId());
            if (merchantEntity != null){
                tEnchashmentBindingEntity.setObjectName(merchantEntity.getMerchantName());
            }
            /*Map map = new HashMap<String, Object>();
            map.put("code",tEnchashmentBindingEntity.getSubbranch());
            List<SysDictEntity> list = sysDictService.selectByMap(map);
            if (list.size() != 0){
                tEnchashmentBindingEntity.setSubbranchName(list.get(0).getValue());
            }*/
        }
        return new PageUtils(page);
    }

}
