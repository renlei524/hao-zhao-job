package com.scxxwb.net.admin.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.operation.dao.MerchantCheckDao;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCheckEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantCheckService;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("merchantCheckService")
public class MerchantCheckServiceImpl extends ServiceImpl<MerchantCheckDao, MerchantCheckEntity> implements MerchantCheckService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    MerchantCheckDao merchantCheckDao;

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
        if (status == null){
            status = "0";
        }
        Page<MerchantCheckEntity> page = this.selectPage(
                new Query<MerchantCheckEntity>(params).getPage(),
                new EntityWrapper<MerchantCheckEntity>()
                    .addFilterIfNeed(StringUtils.isNotBlank(merchantName), "(merchant_name like '%" + merchantName + "%'"
                        + "or merchant_code like '%" + merchantName + "%'"
                        + "or login_username like '%" + merchantName +"%'"
                        + "or agent_id in (select dept_id from t_sys_dept where name like '%" + merchantName + "%'))")
                    .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "agent_id in (" + agentId + ")")
                    .addFilterIfNeed(!status.equals("0"), "status =" + status)
                    .addFilterIfNeed(status.equals("0"),"status in (1, 3)")
                    .orderBy(status.equals("0") ,"status")
        );
        for(MerchantCheckEntity merchantCheckEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchantCheckEntity.getAgentId());
            if (sysDeptEntity != null){
                merchantCheckEntity.setAgentName(sysDeptEntity.getName());
            }
            SysUserEntity sysUserEntity =sysUserService.selectById(merchantCheckEntity.getSysUserId());
            if (sysUserEntity != null){
                merchantCheckEntity.setSysUserName(sysUserEntity.getUserName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void deletByMerchantIds(Integer[] merchantIds) {
        merchantCheckDao.deleteByMerchantIds(merchantIds);
    }

    @Override
    public MerchantCheckEntity selectByMerchantId(Integer merchantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        return merchantCheckDao.selectByMerchantId(map);
    }
}
