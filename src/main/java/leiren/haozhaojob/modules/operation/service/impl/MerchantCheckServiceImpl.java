package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.dao.MerchantCheckDao;
import leiren.haozhaojob.modules.operation.entity.MerchantCheckEntity;
import leiren.haozhaojob.modules.operation.service.GoodsService;
import leiren.haozhaojob.modules.operation.service.MerchantCheckService;
import leiren.haozhaojob.modules.operation.service.WbOrderService;
import leiren.haozhaojob.modules.sys.entity.SysDeptEntity;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysDeptService;
import leiren.haozhaojob.modules.sys.service.SysUserService;
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
    private WbOrderService tWbOrderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    MerchantCheckDao merchantCheckDao;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list = null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            list = Arrays.asList(num.split(","));
        }
        String agentId = StringUtils.join(list,",");
        String merchantName = (String)params.get("merchantName");
        String status = (String)params.get("status");
        String contractNumber = (String)params.get("contractNumber");
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
                    .addFilterIfNeed(StringUtils.isNotBlank(contractNumber), "contract_number like '%" + contractNumber +"%'")
                    .addFilterIfNeed(!status.equals("0") && !status.equals("-1"), "status =" + status)
                    .addFilterIfNeed(status.equals("0"),"status in (1, 3)")
                    .orderBy(status.equals("0") ,"status")
        );
        for(MerchantCheckEntity merchantCheckEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchantCheckEntity.getAgentId());
            if (sysDeptEntity != null){
                merchantCheckEntity.setAgentName(sysDeptEntity.getName());
            }
            //获取客户经理名称 &&手机号
            SysUserEntity sysUserEntity =sysUserService.selectById(merchantCheckEntity.getSalesman());
            if (sysUserEntity != null){
                merchantCheckEntity.setSalesmanName(sysUserEntity.getRealName());
                merchantCheckEntity.setSalesmanMobile(sysUserEntity.getMobile());
            }
            //获取总收入
            merchantCheckEntity.setTotalIncome(tWbOrderService.totalIncomeByMerchantId(merchantCheckEntity.getMerchantId()));
            //获取商品数量
            merchantCheckEntity.setGoodsNumber(goodsService.totalByMerchantId(merchantCheckEntity.getMerchantId()));
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
