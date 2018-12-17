package leiren.haozhaojob.modules.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import leiren.haozhaojob.modules.operation.dao.EnchashmentBindingCheckDao;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingCheckEntity;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.EnchashmentBindingCheckService;
import leiren.haozhaojob.modules.operation.service.EnchashmentBindingService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("tEnchashmentBindingCheckService")
public class EnchashmentBindingCheckServiceImpl extends ServiceImpl<EnchashmentBindingCheckDao, EnchashmentBindingCheckEntity> implements EnchashmentBindingCheckService {
    @Autowired
    MerchantService merchantService;
    @Autowired
    private EnchashmentBindingService enchashmentBindingService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询参数
        String queryName = (String)params.get("merchantName");
        //获取状态选择条件 d
        String statusSearch = (String)params.get("status");
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

        Page<EnchashmentBindingCheckEntity> page = this.selectPage(
                new Query<EnchashmentBindingCheckEntity>(params).getPage(),
                new EntityWrapper<EnchashmentBindingCheckEntity>()
                        .or(flag, "account LIKE '%" + queryName + "%'")
                        .or(flag, "subbranch LIKE '%" + queryName + "%'")
                        .or(flag, "account_name LIKE '%" + queryName + "%'")
                        .or(flag, "object_id IN (" + merchantId + ")")
                        .andNew(StringUtils.isNotBlank(statusSearch), "status = " + statusSearch)
                        .addFilter("status IN (2,4)")
        );

        //添加商户名称及开户行名称
        for (EnchashmentBindingCheckEntity enchashmentBindingCheckEntity : page.getRecords()){
            MerchantEntity merchantEntity =merchantService.selectById(enchashmentBindingCheckEntity.getObjectId());
            if (merchantEntity != null){
                enchashmentBindingCheckEntity.setObjectName(merchantEntity.getMerchantName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void checkEnchashmentBindingCheck(EnchashmentBindingCheckEntity enchashmentBindingCheckEntity) {
        //获取当前审核状态信息 d
        Integer checkStatus = enchashmentBindingCheckEntity.getCheckStatus();
        //获取银行卡状态信息
        Integer status = enchashmentBindingCheckEntity.getStatus();
        //判断审核类型
        if (checkStatus == 1) {
            //修改审核状态为正常
            enchashmentBindingCheckEntity.setStatus(1);
            this.updateAllColumnById(enchashmentBindingCheckEntity);
            //获取英航卡id
            Integer id = enchashmentBindingCheckEntity.getEnchashmentId();
            // 银行卡表
            String enchashmentBindingCheckEntityJson = JSONObject.toJSONString(enchashmentBindingCheckEntity);
            EnchashmentBindingEntity enchashmentBindingEntity = JSONObject.toJavaObject(JSONObject.parseObject(enchashmentBindingCheckEntityJson), EnchashmentBindingEntity.class);
            enchashmentBindingEntity.setId(id);
            enchashmentBindingService.updateAllColumnById(enchashmentBindingEntity);
        } else if (checkStatus == 0) {
            if (status == 2) {
                //修改审核状态为审核不通过
                enchashmentBindingCheckEntity.setStatus(3);
                this.updateAllColumnById(enchashmentBindingCheckEntity);
                //获取英航卡id
                Integer id = enchashmentBindingCheckEntity.getEnchashmentId();
                // 银行卡表
                String enchashmentBindingCheckEntityJson = JSONObject.toJSONString(enchashmentBindingCheckEntity);
                EnchashmentBindingEntity enchashmentBindingEntity = JSONObject.toJavaObject(JSONObject.parseObject(enchashmentBindingCheckEntityJson), EnchashmentBindingEntity.class);
                enchashmentBindingEntity.setId(id);
                enchashmentBindingService.updateAllColumnById(enchashmentBindingEntity);
            } else if (status == 4) {
                //修改审核状态为修改不通过
                enchashmentBindingCheckEntity.setStatus(5);
                this.updateAllColumnById(enchashmentBindingCheckEntity);
                //获取英航卡id
                Integer id = enchashmentBindingCheckEntity.getEnchashmentId();
                // 银行卡表
                String enchashmentBindingCheckEntityJson = JSONObject.toJSONString(enchashmentBindingCheckEntity);
                EnchashmentBindingEntity enchashmentBindingEntity = JSONObject.toJavaObject(JSONObject.parseObject(enchashmentBindingCheckEntityJson), EnchashmentBindingEntity.class);
                enchashmentBindingEntity.setId(id);
                enchashmentBindingService.updateAllColumnById(enchashmentBindingEntity);
            }
        }
    }

    @Override
    public EnchashmentBindingCheckEntity selectEnchashmentBindingCheckByEnchashmentIdAndStatus(EnchashmentBindingCheckEntity enchashmentBindingCheckEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("enchashment_id", enchashmentBindingCheckEntity.getEnchashmentId());
        map.put("status", enchashmentBindingCheckEntity.getStatus());
        List<EnchashmentBindingCheckEntity> list = this.selectByMap(map);
        if (!CollectionUtils.isEmpty(list)) {
            EnchashmentBindingCheckEntity enchashmentBindingCheckEntity1 = list.get(0);
            return enchashmentBindingCheckEntity1;
        }
        return null;
    }

}
