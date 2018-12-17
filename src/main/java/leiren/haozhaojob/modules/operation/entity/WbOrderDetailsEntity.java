package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单明细数据表0
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-29 14:54:25
 */
@TableName("t_wb_order_details")
public class WbOrderDetailsEntity implements Serializable {
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
	 * 商品ID
	 */
	private Integer goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 原始单价
	 */
	private Integer price;
	/**
	 * 最终价
	 */
	private Integer finalPrice;
	/**
	 * 商品数量
	 */
	private Integer quantity;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;

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
	 * 设置：商品ID
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：商品ID
	 */
	public Integer getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：原始单价
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * 获取：原始单价
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * 设置：最终价
	 */
	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}
	/**
	 * 获取：最终价
	 */
	public Integer getFinalPrice() {
		return finalPrice;
	}
	/**
	 * 设置：商品数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * 获取：商品数量
	 */
	public Integer getQuantity() {
		return quantity;
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
}
