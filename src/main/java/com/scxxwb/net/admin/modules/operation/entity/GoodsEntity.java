package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
@TableName("t_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Integer id;
	/**
	 * 商户ID
	 */
	@NotNull(message="商户不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer merchantId;
	/**
	 * 商品名称
	 */
	@NotNull(message="商品名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String goodsName;
	/**
	 * 商品详细
	 */
	@NotNull(message="商品详细不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String details;
	/**
	 * 成本价
	 */
	@NotNull(message="成本价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer originalPrice;
	/**
	 * 市场价
	 */
	@NotNull(message="市场价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer marketPrice;
	/**
	 * 折后价
	 */
	@NotNull(message="折后价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer discountPrice;
	/**
	 * 商品总库存
	 */
	@NotNull(message="商品总库存不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long total;
	/**
	 * 商品销量
	 */
	@NotNull(message="商品销量不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long sales;
	/**
	 * 状态1为正常 2为暂停销售 3删除
	 */
	private Integer status;
	/**
	 * 商品图片
	 */
	private String pictures;
	/**
	 * 商品促销   1为促销   0为不促销
	 */
	private Boolean isPromotion;
	/**
	 * 促销价
	 */
	@NotNull(message="促销价不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer promotionalPrice;
	/**
	 * 剩余库存
	 */
	@NotNull(message="剩余库存不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer surplusInventory;
	/**
	 * 限购数量
	 */
	@NotNull(message="限购数量不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer quantityPurchased;
	/**
	 * 开始时间
	 */
	@NotNull(message="开始时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@NotNull(message="结束时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date endTime;
	/**
	 * 
	 */
	private String tradeContent;
	/**
	 * 
	 */
	private String remark;

	/**
	 * 设置：商品id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：商户ID
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：商户ID
	 */
	public Integer getMerchantId() {
		return merchantId;
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
	 * 设置：商品详细
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * 获取：商品详细
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * 设置：成本价
	 */
	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * 获取：成本价
	 */
	public Integer getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * 设置：市场价
	 */
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * 获取：市场价
	 */
	public Integer getMarketPrice() {
		return marketPrice;
	}
	/**
	 * 设置：折后价
	 */
	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取：折后价
	 */
	public Integer getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置：商品总库存
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * 获取：商品总库存
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * 设置：商品销量
	 */
	public void setSales(Long sales) {
		this.sales = sales;
	}
	/**
	 * 获取：商品销量
	 */
	public Long getSales() {
		return sales;
	}
	/**
	 * 设置：状态1为正常 2为暂停销售 3删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态1为正常 2为暂停销售 3删除
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商品图片
	 */
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	/**
	 * 获取：商品图片
	 */
	public String getPictures() {
		return pictures;
	}
	/**
	 * 设置：商品促销   1为促销   0为不促销
	 */
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	/**
	 * 获取：商品促销   1为促销   0为不促销
	 */
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	/**
	 * 设置：促销价
	 */
	public void setPromotionalPrice(Integer promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}
	/**
	 * 获取：促销价
	 */
	public Integer getPromotionalPrice() {
		return promotionalPrice;
	}
	/**
	 * 设置：剩余库存
	 */
	public void setSurplusInventory(Integer surplusInventory) {
		this.surplusInventory = surplusInventory;
	}
	/**
	 * 获取：剩余库存
	 */
	public Integer getSurplusInventory() {
		return surplusInventory;
	}
	/**
	 * 设置：限购数量
	 */
	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}
	/**
	 * 获取：限购数量
	 */
	public Integer getQuantityPurchased() {
		return quantityPurchased;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：
	 */
	public void setTradeContent(String tradeContent) {
		this.tradeContent = tradeContent;
	}
	/**
	 * 获取：
	 */
	public String getTradeContent() {
		return tradeContent;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
}
