package leiren.haozhaojob.modules.statistics.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

/**
 * 财务核账统计
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
@TableName("t_merchant_finance_flow")
public class CheckAccountsTotalEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 商户昵称
     */
    @TableField(exist = false)
    private String merchantName;
    /**
     * 商户id
     */
    private Integer merchantFinanceId;
    /**
     * 商户电话
     */
    @TableField(exist = false)
    private String telephone;
    /**
     * 昨日余额
     */
    @TableField(exist = false)
    private BigDecimal yesterdayBalance;
    /**
     * 昨日总余额
     */
    @TableField(exist = false)
    private BigDecimal yesterdayTotalBlance;
    /**
     * 今日收入
     */
    @TableField(exist = false)
    private BigDecimal todayIncome;
    /**
     * 今日总收入
     */
    @TableField(exist = false)
    private BigDecimal todayTotalIncome;
    /**
     * 今日支出
     */
    @TableField(exist = false)
    private BigDecimal todayExpend;
    /**
     * 今日总支出
     */
    @TableField(exist = false)
    private BigDecimal todayTotalExpend;
    /**
     * 当前余额
     */
    @TableField(exist = false)
    private BigDecimal currentBalance;
    /**
     * 当前总余额
     */
    @TableField(exist = false)
    private BigDecimal currentTotalBalance;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public BigDecimal getYesterdayBalance() {
        return yesterdayBalance;
    }

    public void setYesterdayBalance(BigDecimal yesterdayBalance) {
        this.yesterdayBalance = yesterdayBalance;
    }

    public BigDecimal getYesterdayTotalBlance() {
        return yesterdayTotalBlance;
    }

    public void setYesterdayTotalBlance(BigDecimal yesterdayTotalBlance) {
        this.yesterdayTotalBlance = yesterdayTotalBlance;
    }

    public BigDecimal getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
    }

    public BigDecimal getTodayExpend() {
        return todayExpend;
    }

    public void setTodayExpend(BigDecimal todayExpend) {
        this.todayExpend = todayExpend;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Integer getMerchantFinanceId() {
        return merchantFinanceId;
    }

    public void setMerchantFinanceId(Integer merchantFinanceId) {

        this.merchantFinanceId = merchantFinanceId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public BigDecimal getTodayTotalIncome() {
        return todayTotalIncome;
    }

    public void setTodayTotalIncome(BigDecimal todayTotalIncome) {
        this.todayTotalIncome = todayTotalIncome;
    }

    public BigDecimal getTodayTotalExpend() {
        return todayTotalExpend;
    }

    public void setTodayTotalExpend(BigDecimal todayTotalExpend) {
        this.todayTotalExpend = todayTotalExpend;
    }

    public BigDecimal getCurrentTotalBalance() {
        return currentTotalBalance;
    }

    public void setCurrentTotalBalance(BigDecimal currentTotalBalance) {
        this.currentTotalBalance = currentTotalBalance;
    }
}
