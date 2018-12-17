package leiren.haozhaojob.modules.statistics.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活跃商户
 */
@TableName("t_merchant")
public class MerchantActiveEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商户id
     */
    private Integer id;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 现金金额
     */
    @TableField(exist = false)
    private BigDecimal cashAmount;
    /**
     * 微宝金额
     */
    @TableField(exist = false)
    private BigDecimal weiBaoAmount;
    /**
     * 优惠券金额
     */
    @TableField(exist = false)
    private BigDecimal couponAmount;
    /**
     * 总单数
     */
    @TableField(exist = false)
    private Long totalBills;

    /**
     * 起始时间
     */
    @TableField(exist = false)
    private Date startDate;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Date endDate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getWeiBaoAmount() {
        return weiBaoAmount;
    }

    public void setWeiBaoAmount(BigDecimal weiBaoAmount) {
        this.weiBaoAmount = weiBaoAmount;
    }

    public Long getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(Long totalBills) {
        this.totalBills = totalBills;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }
}
