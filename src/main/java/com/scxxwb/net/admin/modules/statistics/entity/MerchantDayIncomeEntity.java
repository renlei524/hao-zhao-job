package com.scxxwb.net.admin.modules.statistics.entity;

import java.util.Date;

/**
 * 商户日收入
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.07.12
 */
public class MerchantDayIncomeEntity {
    /**
     * 分公司名称
     */
    private String agentName;

    /**
     * 业务员
     */
    private String salesman;

    /**
     * 商户id
     */
    private String merchantId;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 手机号码
     */
    private  String phoneNumber;

    /**
     * 交易金额
     */
    private  double transactionAmount;

    /**
     * 交易笔数
     */
    private  Integer transactionPenNumber;

    /**
     * 微信笔数
     */
    private Integer weChatPens;

    /**
     * 支付宝笔数
     */
    private  Integer alipayPens;

    /**
     * 交易人数
     */
    private Integer transactionNumber;

    /**
     * 信用卡金额
     */
    private double creditCardAmount;

    /**
     * 信用卡笔数
     */
    private Integer creditCardNumber;

    /**
     * 微信评论
     */
    private Integer weChatReview;

    /**
     * 支付宝评论
     */
    private Integer alipayReview;

    /**
     * 交易日期
     */
    private Date dateOfTransaction;

    /**
     * 序号
     */
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getTransactionPenNumber() {
        return transactionPenNumber;
    }

    public void setTransactionPenNumber(Integer transactionPenNumber) {
        this.transactionPenNumber = transactionPenNumber;
    }

    public Integer getWeChatPens() {
        return weChatPens;
    }

    public void setWeChatPens(Integer weChatPens) {
        this.weChatPens = weChatPens;
    }

    public Integer getAlipayPens() {
        return alipayPens;
    }

    public void setAlipayPens(Integer alipayPens) {
        this.alipayPens = alipayPens;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public double getCreditCardAmount() {
        return creditCardAmount;
    }

    public void setCreditCardAmount(double creditCardAmount) {
        this.creditCardAmount = creditCardAmount;
    }

    public Integer getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Integer creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Integer getWeChatReview() {
        return weChatReview;
    }

    public void setWeChatReview(Integer weChatReview) {
        this.weChatReview = weChatReview;
    }

    public Integer getAlipayReview() {
        return alipayReview;
    }

    public void setAlipayReview(Integer alipayReview) {
        this.alipayReview = alipayReview;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
}
