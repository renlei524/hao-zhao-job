package leiren.haozhaojob.modules.statistics.service.impl;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.statistics.dao.WbOrderBillDao;
import leiren.haozhaojob.modules.statistics.entity.WbOrderBillEntity;
import leiren.haozhaojob.modules.statistics.service.WbOrderBillService;


@Service("wbOrderBillService")
public class WbOrderBillServiceImpl extends ServiceImpl<WbOrderBillDao, WbOrderBillEntity> implements WbOrderBillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String)params.get("userId");
        Page<WbOrderBillEntity> page = this.selectPage(
                new Query<WbOrderBillEntity>(params).getPage(),
                new EntityWrapper<WbOrderBillEntity>()
                .addFilterIfNeed(StringUtils.isNotBlank(userId),"type = 1 AND order_id IN (SELECT order_id FROM t_wb_order WHERE user_id = "+userId+")")
        );

        for (WbOrderBillEntity wbOrderBillEntity : page.getRecords()){
            wbOrderBillEntity.setTotalFee(wbOrderBillEntity.getTotalFee().divide(new BigDecimal("100")));
        }

        return new PageUtils(page);
    }

}
