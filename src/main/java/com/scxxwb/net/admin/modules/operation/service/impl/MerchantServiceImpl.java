package com.scxxwb.net.admin.modules.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCategoryEntity;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCheckEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantCategoryService;
import com.scxxwb.net.admin.modules.operation.service.MerchantCheckService;
import com.scxxwb.net.admin.modules.operation.service.TWbOrderService;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.MerchantDao;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import org.springframework.transaction.annotation.Transactional;

@Service("merchantService")
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, MerchantEntity> implements MerchantService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MerchantCategoryService merchantCategoryService;
    @Autowired
    private MerchantCheckService merchantCheckService;

    @Autowired
    private TWbOrderService tWbOrderService;

    @Autowired
    private MerchantDao merchantDao;

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
        String agentId = StringUtils.join(list,",");
        String merchantName = (String)params.get("merchantName");
        String status = (String)params.get("status");
        String contractNumber = (String)params.get("contractNumber");
        if (status == null){
            status = "0";
        }
        Page<MerchantEntity> page = this.selectPage(
            new Query<MerchantEntity>(params).getPage(),
            new EntityWrapper<MerchantEntity>()
                .addFilterIfNeed(StringUtils.isNotBlank(merchantName), "(merchant_name like '%" + merchantName + "%'"
                    + "or merchant_code like '%" + merchantName + "%'"
                    + "or login_username like '%" + merchantName +"%'"
                    + "or agent_id in (select dept_id from t_sys_dept where name like '%" + merchantName + "%'))")
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "agent_id in (" + agentId + ")")
                .addFilterIfNeed(!status.equals("0"), "status =" + status)
                .addFilterIfNeed(StringUtils.isNotBlank(contractNumber), "contract_number like '%" + contractNumber +"%'")
                .orderBy("create_time", false)
                .orderBy(status.equals("0") ,"status")
        );
        for(MerchantEntity merchantEntity : page.getRecords()){
            //获取部门名称
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchantEntity.getAgentId());
            if (sysDeptEntity != null){
                merchantEntity.setAgentName(sysDeptEntity.getName());
            }
            //获取客户经理名称 &&手机号
            SysUserEntity sysUserEntity =sysUserService.selectById(merchantEntity.getSalesman());
            if (sysUserEntity != null){
                merchantEntity.setSalesmanName(sysUserEntity.getRealName());
                merchantEntity.setSalesmanMobile(sysUserEntity.getMobile());
            }
            //获取总收入
            merchantEntity.setTotalIncome(tWbOrderService.totalIncomeByMerchantId(merchantEntity.getId()));
        }
        return new PageUtils(page);
    }
    @Override
    public List<Integer> getMerchant(List<String> ages) {
        return baseMapper.getMerchant(ages);
    }

    @Override
    public Integer getMerchantByLoginUserName(Map<String, Object> params) {
        return baseMapper.getMerchantByLoginUserName(params);
    }

    @Override
    public MerchantEntity getMerchantByCode(Map<String, Object> params) {
        return baseMapper.getMerchantByCode(params);
    }

    @Override
    public MerchantEntity getMerchantByMerchantName(Map<String, Object> params) {
        return baseMapper.getMerchantByMerchantName(params);
    }

    @Override
    public int getMerchantByAreaTotal(Map<String, Object> params) {
        return baseMapper.getMerchantByAreaTotal(params);
    }

    @Override
    public List<MerchantEntity> getMerchantByArea(Map<String, Object> params) {
        List<MerchantEntity>  merchantEntity = baseMapper.getMerchantByArea(params);
        if (merchantEntity.size() < 100){
            for (MerchantEntity merchant : merchantEntity) {
                SysUserEntity sysUserEntity = sysUserService.selectById(merchant.getSalesman());
                if (sysUserEntity != null) {
                    merchant.setSalesmanName(sysUserEntity.getRealName());
                }
                MerchantCategoryEntity merchantCategoryEntity = merchantCategoryService.selectById(merchant.getTypeId());
                if (merchantCategoryEntity != null) {
                    merchant.setTypeName(merchantCategoryEntity.getName());
                }
            }
        }
        return merchantEntity;
    }

    @Override
    public Boolean updateStatus(Integer id, Integer status) {
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("status", status);
        return merchantDao.updateStatus(map);
    }

    @Override
    public Boolean updateStatusAndRemark(Integer id, Integer status, String remark) {
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("status", status);
        map.put("remark", remark);
        return merchantDao.updateStatusAndRemark(map);
    }

    @Override
    @Transactional
    public void updateMerchant(MerchantEntity merchant) {
        // 非正常和禁用状态下，改为修改待审核
        if(merchant.getStatus() != 5 && merchant.getStatus() != 6) {
            if(merchant.getStatus() != 1) {
                // 修改待审核
                merchant.setStatus(3);
                this.updateStatus(merchant.getId(), merchant.getStatus());
            } else {
                this.updateAllColumnById(merchant);
            }
        }

        // 审核表
        String merchantJson = JSONObject.toJSONString(merchant);
        MerchantCheckEntity merchantCheckEntity = merchantCheckService.selectByMerchantId(merchant.getId());
        if(merchantCheckEntity == null) {
            merchantCheckEntity = JSONObject.toJavaObject(JSONObject.parseObject(merchantJson), MerchantCheckEntity.class);
            merchantCheckEntity.setMerchantId(merchant.getId());
            merchantCheckEntity.setStatus(3);
            merchantCheckEntity.setId(null);
            merchantCheckService.insert(merchantCheckEntity);
        } else {
            Integer merchantCheckEntityId = merchantCheckEntity.getId();
            merchantCheckEntity = JSONObject.toJavaObject(JSONObject.parseObject(merchantJson), MerchantCheckEntity.class);
            merchantCheckEntity.setMerchantId(merchant.getId());
            merchantCheckEntity.setId(merchantCheckEntityId);
            merchantCheckEntity.setStatus(3);
            merchantCheckEntity.setRemark(null);
            merchantCheckService.updateAllColumnById(merchantCheckEntity);
        }
    }

    @Override
    @Transactional
    public void saveMerchant(MerchantEntity merchant, Map map) {
        this.insert(merchant);

        // 审核表
        String merchantJson = JSONObject.toJSONString(merchant);
        MerchantCheckEntity merchantCheckEntity = JSONObject.toJavaObject(JSONObject.parseObject(merchantJson), MerchantCheckEntity.class);
        Integer id = this.getMerchantByLoginUserName(map);
        merchantCheckEntity.setMerchantId(id);
        merchantCheckService.insert(merchantCheckEntity);
    }

    @Override
    @Transactional
    public void stopMerchant(Integer[] ids) {
        for(Integer id : ids) {
            // 禁用
            this.updateStatus(id, 6);
        }
    }

    @Override
    @Transactional
    public void startMerchant(Integer[] ids) {
        for(Integer id : ids) {
            // 启用
            this.updateStatus(id, 5);
        }
    }
}
