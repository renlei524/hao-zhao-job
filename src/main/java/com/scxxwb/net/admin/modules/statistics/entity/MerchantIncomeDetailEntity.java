package com.scxxwb.net.admin.modules.statistics.entity;

import java.util.Date;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public class MerchantIncomeDetailEntity {

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 业务员
     */
    private String sysUserName;

    /**
     * 商户类型
     */
    private String typeName;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 收入笔数
     */
    private  Integer incomePen;

    /**
     * 总支出
     */
    private Double totalExpenditure;

    /**
     * 支出笔数
     */
    private Integer expenditurePens;

    /**
     * 余额
     */
    private  Double balance;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Integer getIncomePen() {
        return incomePen;
    }

    public void setIncomePen(Integer incomePen) {
        this.incomePen = incomePen;
    }

    public Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public Integer getExpenditurePens() {
        return expenditurePens;
    }

    public void setExpenditurePens(Integer expenditurePens) {
        this.expenditurePens = expenditurePens;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
