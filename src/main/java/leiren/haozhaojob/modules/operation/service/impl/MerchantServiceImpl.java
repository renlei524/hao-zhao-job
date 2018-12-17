package leiren.haozhaojob.modules.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.modules.operation.entity.MerchantCategoryEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantCheckEntity;
import leiren.haozhaojob.modules.operation.service.*;
import leiren.haozhaojob.modules.sys.entity.SysDeptEntity;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysDeptService;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.MerchantDao;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
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
    private WbOrderService tWbOrderService;

    @Autowired
    private GoodsService goodsService;

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
            //获取商品数量
            merchantEntity.setGoodsNumber(goodsService.totalByMerchantId(merchantEntity.getId()));
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
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchant.getAgentId());
                if (sysDeptEntity != null){
                    merchant.setAgentName(sysDeptEntity.getName());
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
    public Boolean updateStatusAndRemarkAndUpdateTime(Integer id, Integer status, String remark, Date updateTime) {
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("status", status);
        map.put("remark", remark);
        map.put("updateTime", updateTime);
        return merchantDao.updateStatusAndRemarkAndUpdateTime(map);
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

    @Override
    public List<Integer> selectMerchantIdByMerchantName(String params) {
        return merchantDao.selectMerchantIdByMerchantName(params);
    }

    @Override
    public MerchantEntity selectMerchantNameSAdressAndSManById(Integer id) {
        return merchantDao.selectMerchantNameSAdressAndSManById(id);
    }
    @Override
    public List<Integer> selectMerchantIdWhereNameLikeSearch(String merchantName) {
        return merchantDao.selectMerchantIdWhereNameLikeSearch(merchantName);
    }

    @Override
    public String selectMerchantNameByCreatorId(Integer creatorId) {
        return merchantDao.selectMerchantNameByCreatorId(creatorId);
    }

    @Override
    public List<Integer> selectMerchantIdsByNameAndMobile(String merchantName, String telephone) {
        return merchantDao.selectMerchantIdsByNameAndMobile(merchantName, telephone);
    }

    @Override
    public MerchantEntity selectMerchantNameAndTelephoneByMerchantId(Integer merchantId) {
        return merchantDao.getMerchantNameAndTelephoneByMerchantId(merchantId);
    }
}
