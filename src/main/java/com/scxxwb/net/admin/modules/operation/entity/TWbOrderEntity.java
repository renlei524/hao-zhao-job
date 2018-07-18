package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单数据表0
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@TableName("t_wb_order")
public class TWbOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	@TableId
	private Integer orderId;
	/**
	 * 父订单id
	 */
	@NotNull(message="父订单id不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String parentOrderId;
	/**
	 * 用户id:正式用户为1开头(正式用户表初始自动增长为500000000) 微信临时用户时是5开头(微信临时表初始自动增长为500000000)，支付宝临时用户为7开关(支付宝临时表初始自动增长为700000000)
	 */
	@NotNull(message="用户名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer userId;
	/**
	 * 用户名称
	 */
	@TableField(exist=false)
	private  String userName;

	/**
	 * 商家id
	 */
	@NotNull(message="商家名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer merchantId;

	/**
	 * 商家名称
	 */
	@TableField(exist=false)
	private String merchantName;

	/**
	 * 订单总价即原价
	 */
	@NotNull(message="订单总价即原价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Double totalAmount;
	/**
	 * 实际给商家的结算价
	 */
	@NotNull(message="实际给商家的结算价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Double settleAccounts;
	/**
	 * 实际支付价
	 */
	@NotNull(message="实际支付价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Double finalAmount;

	/**
	 * 微宝抵扣
	 */
	private Double wbDeductible;

    /**
     * 手机号
     */
	private String mobile;

    /**
     * 类型
     */
	private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getWbDeductible() {
		return wbDeductible;
	}

	public void setWbDeductible(Double wbDeductible) {
		this.wbDeductible = wbDeductible;
	}

	/**
	 * 判断最后支付金额是不是来自信用卡
	 */
	private String isFinalAmountFrom;
	/**
	 * 订单状态 0:待支付 1:已支付 2:已完成
	 */
	private Integer status;
	/**
	 * 如果order_from值是微信那么这里是union_id;order_from值是支付宝那么这里是获取支付宝的唯一id
	 */
	private String unionId;
	/**
	 * 订单来源(1.微信 2.支付宝)
	 */
	private Integer orderFrom;
	/**
	 * 订单类型(1.扫码付2.商城)
	 */
	private Integer orderType;
	/**
	 * 下单时间
	 */
	@NotNull(message="下单时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Date createTime;
	/**
	 * 支付完成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date payTime;
	/**
	 * 用户删除
	 */
	private Boolean userDelete;
	/**
	 * 商户删除
	 */
	private Boolean merchantDelete;
	/**
	 * 银行通道类型；1=原生，2=点点客
	 */
	private Integer payChannel;
	/**
	 * 重要信息加密后存放于此，方便后面的验证订单数据完整性
	 */
	private String encryptedData;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：订单ID
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单ID
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：父订单id
	 */
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	/**
	 * 获取：父订单id
	 */
	public String getParentOrderId() {
		return parentOrderId;
	}
	/**
	 * 设置：用户id:正式用户为1开头(正式用户表初始自动增长为500000000) 微信临时用户时是5开头(微信临时表初始自动增长为500000000)，支付宝临时用户为7开关(支付宝临时表初始自动增长为700000000)
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id:正式用户为1开头(正式用户表初始自动增长为500000000) 微信临时用户时是5开头(微信临时表初始自动增长为500000000)，支付宝临时用户为7开关(支付宝临时表初始自动增长为700000000)
	 */
	public Integer getUserId() {
		return userId;
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
	 * 设置：订单总价即原价
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取：订单总价即原价
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * 设置：实际给商家的结算价
	 */
	public void setSettleAccounts(Double settleAccounts) {
		this.settleAccounts = settleAccounts;
	}
	/**
	 * 获取：实际给商家的结算价
	 */
	public Double getSettleAccounts() {
		return settleAccounts;
	}
	/**
	 * 设置：实际支付价
	 */
	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}
	/**
	 * 获取：实际支付价
	 */
	public Double getFinalAmount() {
		return finalAmount;
	}
	/**
	 * 设置：判断最后支付金额是不是来自信用卡
	 */
	public void setIsFinalAmountFrom(String isFinalAmountFrom) {
		this.isFinalAmountFrom = isFinalAmountFrom;
	}
	/**
	 * 获取：判断最后支付金额是不是来自信用卡
	 */
	public String getIsFinalAmountFrom() {
		return isFinalAmountFrom;
	}
	/**
	 * 设置：订单状态 0:待支付 1:已支付 2:已完成
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：订单状态 0:待支付 1:已支付 2:已完成
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：如果order_from值是微信那么这里是union_id;order_from值是支付宝那么这里是获取支付宝的唯一id
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	/**
	 * 获取：如果order_from值是微信那么这里是union_id;order_from值是支付宝那么这里是获取支付宝的唯一id
	 */
	public String getUnionId() {
		return unionId;
	}
	/**
	 * 设置：订单来源(1.微信 2.支付宝)
	 */
	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}
	/**
	 * 获取：订单来源(1.微信 2.支付宝)
	 */
	public Integer getOrderFrom() {
		return orderFrom;
	}
	/**
	 * 设置：订单类型(1.扫码付2.商城)
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：订单类型(1.扫码付2.商城)
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：下单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：下单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：支付完成时间
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * 获取：支付完成时间
	 */
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * 设置：用户删除
	 */
	public void setUserDelete(Boolean userDelete) {
		this.userDelete = userDelete;
	}
	/**
	 * 获取：用户删除
	 */
	public Boolean getUserDelete() {
		return userDelete;
	}
	/**
	 * 设置：商户删除
	 */
	public void setMerchantDelete(Boolean merchantDelete) {
		this.merchantDelete = merchantDelete;
	}
	/**
	 * 获取：商户删除
	 */
	public Boolean getMerchantDelete() {
		return merchantDelete;
	}
	/**
	 * 设置：银行通道类型；1=原生，2=点点客
	 */
	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}
	/**
	 * 获取：银行通道类型；1=原生，2=点点客
	 */
	public Integer getPayChannel() {
		return payChannel;
	}
	/**
	 * 设置：重要信息加密后存放于此，方便后面的验证订单数据完整性
	 */
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	/**
	 * 获取：重要信息加密后存放于此，方便后面的验证订单数据完整性
	 */
	public String getEncryptedData() {
		return encryptedData;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantName() {
		return merchantName;
	}
}
