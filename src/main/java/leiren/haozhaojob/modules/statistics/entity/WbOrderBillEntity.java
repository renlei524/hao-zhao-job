package leiren.haozhaojob.modules.statistics.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算单据表0
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-17 14:11:41
 */
@TableName("t_wb_order_bill")
public class WbOrderBillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id,自动增长
	 */
	@TableId
	private Integer id;
	/**
	 * 订单ID
	 */
	private Integer orderId;
	/**
	 * 该笔单据的金额
	 */
	private BigDecimal totalFee;
	/**
	 * 1.微宝 2.优惠券 3.支付宝 4.微信
	 */
	private Integer type;
	/**
	 * 
	 */
	private Integer couponId;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：主键id,自动增长
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id,自动增长
	 */
	public Integer getId() {
		return id;
	}
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
	 * 设置：该笔单据的金额
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * 获取：该笔单据的金额
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	/**
	 * 设置：1.微宝 2.优惠券 3.支付宝 4.微信
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1.微宝 2.优惠券 3.支付宝 4.微信
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	/**
	 * 获取：
	 */
	public Integer getCouponId() {
		return couponId;
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
}
