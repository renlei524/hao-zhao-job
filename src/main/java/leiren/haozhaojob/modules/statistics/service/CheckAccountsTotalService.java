package leiren.haozhaojob.modules.statistics.service;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.CheckAccountsTotalEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 财务核账统计
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public interface CheckAccountsTotalService {

    PageUtils queryPage(Map<String, Object> params);

    BigDecimal selectBalanceByMerchantId(Integer merchantId, Date date);

    BigDecimal selectTodayIncomeByMerchantId(Integer merchantId, Date startDate, Date endDate);

    BigDecimal selectTodayExpendByMerchantId(Integer merchantId, Date startDate, Date endDate);

    BigDecimal selectTotalBalanceAllByDate(Date startDate, Date endDate, String merchantIds);

    BigDecimal selectTodayTotalIncomeByDate(Date startDate, Date endDate, String merchantIds);

    BigDecimal selectTodayTotalExpendByDate(Date startDate, Date endDate, String merchantIds);

    CheckAccountsTotalEntity selectCheckAccountsByDate(Map<String, Object> params);
}
