package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息审核
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
@TableName("t_goods_check")
public class GoodsCheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Integer id;
	/**
	 * 商户ID
	 */
	private Integer merchantId;

    /**
     * 商户名称
     */
    @TableField(exist=false)
	private String merchantName;

	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品详细
	 */
	private String details;
	/**
	 * 成本价
	 */
	private BigDecimal originalPrice;
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	/**
	 * 折后价
	 */
	private BigDecimal discountPrice;
	/**
	 * 商品总库存
	 */
	private Integer total;
	/**
	 * '审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：暂停销售 7：售罄 8:删除
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
	private BigDecimal promotionalPrice;
	/**
	 * 剩余库存
	 */
	private Integer surplusInventory;
	/**
	 * 限购数量
	 */
	private Integer quantityPurchased;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date endTime;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 商品标签Id
	 */
	private Integer labelId;

	/**
	 * 商品标签名称
	 */
	@TableField(exist=false)
	private String labelName;

	/**
	 * 是否推荐
	 */
	private Boolean isRecommend;
	/**
	 * 点赞数量
	 */
	private Integer thumbsUp;

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
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * 获取：成本价
	 */
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * 设置：市场价
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * 获取：市场价
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	/**
	 * 设置：折后价
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取：折后价
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置：商品总库存
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取：商品总库存
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置：'审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：暂停销售 7：售罄 8:删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：'审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：暂停销售 7：售罄 8:删除
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
	public void setPromotionalPrice(BigDecimal promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}
	/**
	 * 获取：促销价
	 */
	public BigDecimal getPromotionalPrice() {
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
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：商品标签Id
	 */
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	/**
	 * 获取：商品标签Id
	 */
	public Integer getLabelId() {
		return labelId;
	}
	/**
	 * 设置：是否推荐
	 */
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	/**
	 * 获取：是否推荐
	 */
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	/**
	 * 设置：点赞数量
	 */
	public void setThumbsUp(Integer thumbsUp) {
		this.thumbsUp = thumbsUp;
	}
	/**
	 * 获取：点赞数量
	 */
	public Integer getThumbsUp() {
		return thumbsUp;
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
