package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.CheckAccountsTotalEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务核账统计
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
@Service
public interface CheckAccountsTotalDao extends BaseMapper<CheckAccountsTotalEntity> {

    BigDecimal getBalanceByMerchantId(Integer merchantId, Date date);

    BigDecimal getTodayIncomeByMerchantId(Integer merchantId, Date startDate, Date endDate);

    BigDecimal getTodayExpendByMerchantId(Integer merchantId, Date startDate, Date endDate);

    BigDecimal getTotalBalanceAllByDate(Date startDate, Date endDate, String merchantIds);

    BigDecimal getTodayTotalIncomeByDate(Date startDate, Date endDate, String merchantIds);

    BigDecimal getTodayTotalExpendByDate(Date startDate, Date endDate, String merchantIds);
}
