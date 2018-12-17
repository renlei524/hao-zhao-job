package leiren.haozhaojob.modules.statistics.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.utils.DateUtils;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.statistics.dao.CheckAccountsTotalDao;
import leiren.haozhaojob.modules.statistics.entity.CheckAccountsTotalEntity;
import leiren.haozhaojob.modules.statistics.service.CheckAccountsTotalService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 财务核账统计
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
@Service("checkAccountsTotalService")
public class CheckAccountsTotalServiceImpl extends ServiceImpl<CheckAccountsTotalDao, CheckAccountsTotalEntity> implements CheckAccountsTotalService {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private CheckAccountsTotalDao checkAccountsTotalDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询参数时间 d
        String time = (String)params.get("time");
        //转换时间格式
        Date startDate = DateUtils.searchStartDateDeal(time);
        Date endDate = DateUtils.searchEndDateDeal(time);
        Date yStartDate = DateUtils.addDateDays(startDate, -1);
        Date yEndDate1 = DateUtils.addDateDays(endDate, -1);
        String yEndDate2 = DateUtils.format(yEndDate1, DateUtils.DATE_TIME_PATTERN);
        Date yEndDate = DateUtils.stringToDate(yEndDate2, DateUtils.DATE_TIME_PATTERN);
        //获取查询参数商户名称 d
        String merchantName = (String)params.get("merchantName");
        //获取查询参数商户电话号码 d
        String merchantMobile = (String)params.get("merchantMobile");
        //声明商户id查询条件字符串 d
        String merchantIdSearch = null;
        //定义条件查询标记 d
        boolean flag = false;
        //根据商户名称和商户电话号码查询商户id集合 d
        if (merchantName != null && !merchantName.isEmpty() || merchantMobile != null && !merchantMobile.isEmpty()) {
            List<Integer> merchantIds = merchantService.selectMerchantIdsByNameAndMobile(merchantName, merchantMobile);
            if (!CollectionUtils.isEmpty(merchantIds)) {
                merchantIdSearch = StringUtils.join(merchantIds, ",");
                flag = true;
            }
        }
        Page<CheckAccountsTotalEntity> page = this.selectPage(
                new Query<CheckAccountsTotalEntity>(params).getPage(),
                new EntityWrapper<CheckAccountsTotalEntity>()
                        .in(flag, "merchant_finance_id", merchantIdSearch)
                        .between("transaction_time", startDate, endDate)
                        .groupBy("merchant_finance_id")
        );
        List<CheckAccountsTotalEntity> checkAccountsTotalEntityList = page.getRecords();
        if (!CollectionUtils.isEmpty(checkAccountsTotalEntityList)) {
            for (CheckAccountsTotalEntity checkAccountsTotalEntity : checkAccountsTotalEntityList) {
                //获取商户id
                Integer merchantFinanceId = checkAccountsTotalEntity.getMerchantFinanceId();
                //根据商户id获取商户昵称和电话
                MerchantEntity merchantEntity = merchantService.selectMerchantNameAndTelephoneByMerchantId(merchantFinanceId);
                //添加商户呢城
                checkAccountsTotalEntity.setMerchantName(merchantEntity.getMerchantName());
                //添加商户电话
                checkAccountsTotalEntity.setTelephone(merchantEntity.getTelphone());
                //添加 根据商户id查询昨日余额a
                BigDecimal bigDecimal = this.selectBalanceByMerchantId(merchantFinanceId, yEndDate);
                checkAccountsTotalEntity.setYesterdayBalance(bigDecimal);
                //添加 今日收入
                BigDecimal bigDecimal1 = this.selectTodayIncomeByMerchantId(merchantFinanceId, startDate, endDate);
                checkAccountsTotalEntity.setTodayIncome(bigDecimal1);
                //添加 几日支出
                BigDecimal bigDecimal2 = this.selectTodayExpendByMerchantId(merchantFinanceId, startDate, endDate);
                checkAccountsTotalEntity.setTodayExpend(bigDecimal2);
                //查询当前余额
                BigDecimal bigDecimal3 = this.selectBalanceByMerchantId(merchantFinanceId, endDate);
                checkAccountsTotalEntity.setCurrentBalance(bigDecimal3);
          }
        }
        return new PageUtils(page);
    }

    @Override
    public BigDecimal selectBalanceByMerchantId(Integer merchantFinanceId, Date date) {
        return checkAccountsTotalDao.getBalanceByMerchantId(merchantFinanceId, date);
    }

    @Override
    public BigDecimal selectTodayIncomeByMerchantId(Integer merchantFinanceId, Date startDate, Date endDate) {
        return checkAccountsTotalDao.getTodayIncomeByMerchantId(merchantFinanceId, startDate, endDate);
    }

    @Override
    public BigDecimal selectTodayExpendByMerchantId(Integer merchantFinanceId, Date startDate, Date endDate) {
        return checkAccountsTotalDao.getTodayExpendByMerchantId(merchantFinanceId, startDate, endDate);
    }

    @Override
    public BigDecimal selectTotalBalanceAllByDate(Date startDate, Date endDate, String merchantIds) {
        return checkAccountsTotalDao.getTotalBalanceAllByDate(startDate, endDate, merchantIds);
    }

    @Override
    public BigDecimal selectTodayTotalIncomeByDate(Date startDate, Date endDate, String merchantIds) {
        return checkAccountsTotalDao.getTodayTotalIncomeByDate(startDate, endDate, merchantIds);
    }

    @Override
    public BigDecimal selectTodayTotalExpendByDate(Date startDate, Date endDate, String merchantIds) {
        return checkAccountsTotalDao.getTodayTotalExpendByDate(startDate, endDate, merchantIds);
    }

    @Override
    public CheckAccountsTotalEntity selectCheckAccountsByDate(Map<String, Object> params) {
        //获取查询参数时间 d
        String time = (String)params.get("time");
        //转换时间格式
        Date startDate = DateUtils.searchStartDateDeal(time);
        Date endDate = DateUtils.searchEndDateDeal(time);
        Date yStartDate = DateUtils.addDateDays(startDate, -1);
        Date yEndDate = DateUtils.addDateDays(endDate, -1);
        //获取查询参数商户名称 d
        String merchantName = (String)params.get("merchantName");
        //获取查询参数商户电话号码 d
        String merchantMobile = (String)params.get("merchantMobile");
        //声明商户id查询条件字符串 d
        String merchantIdSearch = null;
        //根据商户名称和商户电话号码查询商户id集合 d
        if (merchantName != null && !merchantName.isEmpty() || merchantMobile != null && !merchantMobile.isEmpty()) {
            List<Integer> merchantIds = merchantService.selectMerchantIdsByNameAndMobile(merchantName, merchantMobile);
            if (!CollectionUtils.isEmpty(merchantIds)) {
                merchantIdSearch = StringUtils.join(merchantIds, ",");
            }
        }
        CheckAccountsTotalEntity checkAccountsTotalEntity = new CheckAccountsTotalEntity();
        //查询昨日总余额
        BigDecimal yTotalBalance = this.selectTotalBalanceAllByDate(yStartDate, yEndDate, merchantIdSearch);
        checkAccountsTotalEntity.setYesterdayTotalBlance(yTotalBalance);
        //查询今日总收入
        BigDecimal curTotalIncome = this.selectTodayTotalIncomeByDate(startDate, endDate, merchantIdSearch);
        checkAccountsTotalEntity.setTodayTotalIncome(curTotalIncome);
        //查询金日总支出
        BigDecimal curTotalExpend = this.selectTodayTotalExpendByDate(startDate, endDate, merchantIdSearch);
        checkAccountsTotalEntity.setTodayTotalExpend(curTotalExpend);
        //查询当前总余额
        BigDecimal curTotalBalance = this.selectTotalBalanceAllByDate(startDate, endDate, merchantIdSearch);
        checkAccountsTotalEntity.setCurrentTotalBalance(curTotalBalance);
        return checkAccountsTotalEntity;
    }
}
