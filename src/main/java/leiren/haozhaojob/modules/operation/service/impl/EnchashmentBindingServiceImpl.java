package leiren.haozhaojob.modules.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingCheckEntity;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.EnchashmentBindingCheckService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.EnchashmentBindingDao;
import leiren.haozhaojob.modules.operation.service.EnchashmentBindingService;
import org.springframework.transaction.annotation.Transactional;


@Service("tEnchashmentBindingService")
public class EnchashmentBindingServiceImpl extends ServiceImpl<EnchashmentBindingDao, EnchashmentBindingEntity> implements EnchashmentBindingService {

    @Autowired
    MerchantService merchantService;
    @Autowired
    private EnchashmentBindingCheckService enchashmentBindingCheckService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询参数
        String queryName = (String) params.get("merchantName");
        String status = (String) params.get("status");
        boolean statusFlag = status != null && status != "" && !status.equals("0");
        boolean flag = StringUtils.isNotBlank((String) params.get("merchantName"));
        String merchantId = null;
        if (queryName != null) {
            //根据商户名称查询出商户信息
            List<MerchantEntity> merchantEntityList = merchantService.getMerchantByArea(params);

            //初始化对象
            List<Integer> merchantIds = new ArrayList<>();
            for (MerchantEntity merchantEntity : merchantEntityList) {
                merchantIds.add(merchantEntity.getId());
            }
            if (merchantIds.size() == 0) {
                merchantIds.add(0);
            }
            merchantId = StringUtils.join(merchantIds, ',');
        }
        Page<EnchashmentBindingEntity> page = this.selectPage(
                new Query<EnchashmentBindingEntity>(params).getPage(),
                new EntityWrapper<EnchashmentBindingEntity>().eq(statusFlag, "status", status)
                        .orNew(flag, "account LIKE '%" + queryName + "%'")
                        .or(flag, "subbranch LIKE '%" + queryName + "%'")
                        .or(flag, "account_name LIKE '%" + queryName + "%'")
                        .or(flag, "object_id IN (" + merchantId + ")")
        );
        //添加商户名称及开户行名称
        for (EnchashmentBindingEntity tEnchashmentBindingEntity : page.getRecords()) {
            MerchantEntity merchantEntity = merchantService.selectById(tEnchashmentBindingEntity.getObjectId());
            if (merchantEntity != null) {
                tEnchashmentBindingEntity.setObjectName(merchantEntity.getMerchantName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveEnchashmentBinding(EnchashmentBindingEntity enchashmentBindingEntity) {
        this.insert(enchashmentBindingEntity);
        //查询新增银行卡id
        EnchashmentBindingEntity enchashmentBindingEntity1 = this.selectOne(new EntityWrapper<EnchashmentBindingEntity>(enchashmentBindingEntity));
        enchashmentBindingEntity.setId(enchashmentBindingEntity1.getId());
        // 审核表
        String enchashmentBindingEntityJson = JSONObject.toJSONString(enchashmentBindingEntity);
        EnchashmentBindingCheckEntity enchashmentBindingCheckEntity = JSONObject.toJavaObject(JSONObject.parseObject(enchashmentBindingEntityJson), EnchashmentBindingCheckEntity.class);
        enchashmentBindingCheckEntity.setEnchashmentId(enchashmentBindingEntity.getId());
        enchashmentBindingCheckService.insertOrUpdate(enchashmentBindingCheckEntity);
    }

    @Override
    @Transactional
    public void updateEnchashmentBinding(EnchashmentBindingEntity enchashmentBindingEntity) {
        this.updateAllColumnById(enchashmentBindingEntity);
        // 审核表
        String enchashmentBindingEntityJson = JSONObject.toJSONString(enchashmentBindingEntity);
        EnchashmentBindingCheckEntity enchashmentBindingCheckEntity = JSONObject.toJavaObject(JSONObject.parseObject(enchashmentBindingEntityJson), EnchashmentBindingCheckEntity.class);
        enchashmentBindingCheckEntity.setEnchashmentId(enchashmentBindingEntity.getId());
        EnchashmentBindingCheckEntity enchashmentBindingEntity1 = enchashmentBindingCheckService.selectEnchashmentBindingCheckByEnchashmentIdAndStatus(enchashmentBindingCheckEntity);
        if (enchashmentBindingEntity1 != null) {
            enchashmentBindingCheckService.updateAllColumnById(enchashmentBindingEntity1);
        } else {
            enchashmentBindingCheckService.insert(enchashmentBindingCheckEntity);
        }
    }

    @Override
    public void stopEnchashmentBinding(Integer[] ids) {
        List<EnchashmentBindingEntity> enchashmentBindingEntityList = new ArrayList<>();
        EnchashmentBindingEntity enchashmentBindingEntity = null;
        for (int i = 0; i < ids.length; i++) {
            enchashmentBindingEntity =  new EnchashmentBindingEntity();
            enchashmentBindingEntity.setId(ids[i]);
            enchashmentBindingEntity.setStatus(6);
            enchashmentBindingEntityList.add(enchashmentBindingEntity);
        }
        this.updateBatchById(enchashmentBindingEntityList);
    }

    @Override
    public void startEnchashmentBinding(Integer[] ids) {
        List<EnchashmentBindingEntity> enchashmentBindingEntityList = new ArrayList<>();
        EnchashmentBindingEntity enchashmentBindingEntity = null;
        for (int i = 0; i < ids.length; i++) {
            enchashmentBindingEntity = new EnchashmentBindingEntity();
            enchashmentBindingEntity.setId(ids[i]);
            enchashmentBindingEntity.setStatus(1);
            enchashmentBindingEntityList.add(enchashmentBindingEntity);
        }
        this.updateBatchById(enchashmentBindingEntityList);
    }

}
