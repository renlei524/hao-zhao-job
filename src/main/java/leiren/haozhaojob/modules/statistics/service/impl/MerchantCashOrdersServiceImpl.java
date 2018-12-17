package leiren.haozhaojob.modules.statistics.service.impl;

import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.statistics.service.MerchantCashOrdersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.statistics.dao.MerchantCashOrdersDao;
import leiren.haozhaojob.modules.statistics.entity.MerchantCashOrdersEntity;


@Service("tMerchantCashOrdersService")
public class MerchantCashOrdersServiceImpl extends ServiceImpl<MerchantCashOrdersDao, MerchantCashOrdersEntity> implements MerchantCashOrdersService {

    @Autowired
    MerchantService merchantService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String merchantName = (String)params.get("merchantName");
        String status = (String)params.get("status");
        String endTime = (String)params.get("endTime");
        String startTime = (String)params.get("startTime");
        if (startTime != null){
            startTime = startTime + " 00:00:00";
        }
        if (endTime != null){
            endTime = endTime + " 23:59:59";
        }
        Page<MerchantCashOrdersEntity> page = this.selectPage(
                new Query<MerchantCashOrdersEntity>(params).getPage(),
                new EntityWrapper<MerchantCashOrdersEntity>()
                .addFilterIfNeed((StringUtils.isNotBlank(status) && !status.equals("0")),"status = " + status)
                .addFilterIfNeed(StringUtils.isNotBlank(merchantName), "merchant_id in (select id from t_merchant where merchant_name like '%" + merchantName + "%')")
                .addFilterIfNeed((StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)),"create_time BETWEEN '" + startTime + "' and '" + endTime + "'")
        );

        for (MerchantCashOrdersEntity tMerchantCashOrdersEntity : page.getRecords()) {
            tMerchantCashOrdersEntity.setTotalAmount(tMerchantCashOrdersEntity.getTotalAmount().divide(new BigDecimal("100")));
            MerchantEntity merchantEntity = merchantService.selectById(tMerchantCashOrdersEntity.getMerchantId());
            if (merchantEntity != null){
                tMerchantCashOrdersEntity.setMerchantName(merchantEntity.getMerchantName());
            }
        }

        return new PageUtils(page);
    }

}
