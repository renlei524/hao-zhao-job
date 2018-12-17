package leiren.haozhaojob.modules.statistics.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商家提现打包记录订单表
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-14 09:27:44
 */
@TableName("t_merchant_cash_orders")
public class MerchantCashOrdersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 提现订单号
	 */
	@TableId
	private Long cashOrderId;

	/**
	 * 商家id
	 */
	private Integer merchantId;

	/**
	 * 商家名称
	 */
    @TableField(exist=false)
	private String merchantName;
	/**
	 * 提现日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date cashDate;
	/**
	 * 提现金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 提现明细总笔数
	 */
	private Integer totalCount;
	/**
	 * 手续费
	 */
	private Integer serviceCharge;
	/**
	 * 提现状态  2已到账 3 待处理 4失败 5已申请
	 */
	private Integer status;
	/**
	 * 提现卡号
	 */
	private String toBankCard;
	/**
	 * 提现银行名称
	 */
	private String toBankName;
	/**
	 * 提现开户名
	 */
	private String toAccountName;
	/**
	 * 提现银行联号
	 */
	private String toBankNo;
	/**
	 * 银行转账完成的时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date finishDate;
	/**
	 * 应答返回码
	 */
	private String errorCode;
	/**
	 * 应答描述
	 */
	private String errorRemark;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 信用卡提现总金额
	 */
	private Integer totalAmountCerdit;
	/**
	 * 提现原始总金额
	 */
	private Integer totalAmountOriginal;

	/**
	 * 设置：提现订单号
	 */
	public void setCashOrderId(Long cashOrderId) {
		this.cashOrderId = cashOrderId;
	}
	/**
	 * 获取：提现订单号
	 */
	public Long getCashOrderId() {
		return cashOrderId;
	}
	/**
	 * 设置：商家id
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：商家id
	 */
	public Integer getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置：提现日期
	 */
	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}
	/**
	 * 获取：提现日期
	 */
	public Date getCashDate() {
		return cashDate;
	}
	/**
	 * 设置：提现金额
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取：提现金额
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	/**
	 * 设置：提现明细总笔数
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 获取：提现明细总笔数
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置：手续费
	 */
	public void setServiceCharge(Integer serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	/**
	 * 获取：手续费
	 */
	public Integer getServiceCharge() {
		return serviceCharge;
	}
	/**
	 * 设置：提现状态  2已到账 3 待处理 4失败 5已申请
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：提现状态  2已到账 3 待处理 4失败 5已申请
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：提现卡号
	 */
	public void setToBankCard(String toBankCard) {
		this.toBankCard = toBankCard;
	}
	/**
	 * 获取：提现卡号
	 */
	public String getToBankCard() {
		return toBankCard;
	}
	/**
	 * 设置：提现银行名称
	 */
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}
	/**
	 * 获取：提现银行名称
	 */
	public String getToBankName() {
		return toBankName;
	}
	/**
	 * 设置：提现开户名
	 */
	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}
	/**
	 * 获取：提现开户名
	 */
	public String getToAccountName() {
		return toAccountName;
	}
	/**
	 * 设置：提现银行联号
	 */
	public void setToBankNo(String toBankNo) {
		this.toBankNo = toBankNo;
	}
	/**
	 * 获取：提现银行联号
	 */
	public String getToBankNo() {
		return toBankNo;
	}
	/**
	 * 设置：银行转账完成的时间
	 */
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	/**
	 * 获取：银行转账完成的时间
	 */
	public Date getFinishDate() {
		return finishDate;
	}
	/**
	 * 设置：应答返回码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * 获取：应答返回码
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * 设置：应答描述
	 */
	public void setErrorRemark(String errorRemark) {
		this.errorRemark = errorRemark;
	}
	/**
	 * 获取：应答描述
	 */
	public String getErrorRemark() {
		return errorRemark;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：信用卡提现总金额
	 */
	public void setTotalAmountCerdit(Integer totalAmountCerdit) {
		this.totalAmountCerdit = totalAmountCerdit;
	}
	/**
	 * 获取：信用卡提现总金额
	 */
	public Integer getTotalAmountCerdit() {
		return totalAmountCerdit;
	}
	/**
	 * 设置：提现原始总金额
	 */
	public void setTotalAmountOriginal(Integer totalAmountOriginal) {
		this.totalAmountOriginal = totalAmountOriginal;
	}
	/**
	 * 获取：提现原始总金额
	 */
	public Integer getTotalAmountOriginal() {
		return totalAmountOriginal;
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
