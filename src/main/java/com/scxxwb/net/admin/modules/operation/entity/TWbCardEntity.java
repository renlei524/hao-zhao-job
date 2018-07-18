package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 卡券表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
@TableName("t_wb_card")
public class TWbCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 发行方类型 1:平台 2:商户
	 */
	private Integer creatorType;
	/**
	 * 创建人/发行人
	 */
	private Integer creatorId;
	/**
	 * 可使用开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	@NotNull(message = "使用时间不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Date beginTime;
	/**
	 * 卡券过期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	@NotNull(message = "过期时间不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Date endTime;
	/**
	 * 卡卷数量
	 */
	@NotNull(message = "卡卷数量不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Integer cardNum;
	/**
	 * 剩余卡券数量
	 */
	@NotNull(message = "剩余卡券数量不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Integer leftNum;
	/**
	 * 用户最多持有张数
	 */
	private Integer limitNum;
	/**
	 * 卡券logo
	 */
	private String logo;
	/**
	 * 使用说明(如:全场通用;不可与打折商品一起使用)
	 */
	private String description;
	/**
	 * 状态 1,未删除；2,已删除
	 */
	private Integer status;
	/**
	 * 发行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	@NotNull(message = "发行时间不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Date createDate;
	/**
	 * 卡券种类(0,代金卷;1,折扣券;2,满减券)
	 */
	private Integer type;
	/**
	 * 折扣(1至99的整数)
	 */
	private Integer discount;
	/**
	 * 满足金额(满减券使用,满金额,满后所减金额用cardmoney保存)
	 */
	private Integer fullMoney;
	/**
	 * 卡券金额
	 */
	@NotNull(message = "卡券金额不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Integer cardMoney;
	/**
	 * 优惠券名称(如:满100减10;全场8折)
	 */
	@NotNull(message = "优惠券名称不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String cardContent;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：发行方类型 1:平台 2:商户
	 */
	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}
	/**
	 * 获取：发行方类型 1:平台 2:商户
	 */
	public Integer getCreatorType() {
		return creatorType;
	}
	/**
	 * 设置：创建人/发行人
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * 获取：创建人/发行人
	 */
	public Integer getCreatorId() {
		return creatorId;
	}
	/**
	 * 设置：可使用开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取：可使用开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置：卡券过期时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：卡券过期时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：卡卷数量
	 */
	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}
	/**
	 * 获取：卡卷数量
	 */
	public Integer getCardNum() {
		return cardNum;
	}
	/**
	 * 设置：剩余卡券数量
	 */
	public void setLeftNum(Integer leftNum) {
		this.leftNum = leftNum;
	}
	/**
	 * 获取：剩余卡券数量
	 */
	public Integer getLeftNum() {
		return leftNum;
	}
	/**
	 * 设置：用户最多持有张数
	 */
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}
	/**
	 * 获取：用户最多持有张数
	 */
	public Integer getLimitNum() {
		return limitNum;
	}
	/**
	 * 设置：卡券logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：卡券logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：使用说明(如:全场通用;不可与打折商品一起使用)
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：使用说明(如:全场通用;不可与打折商品一起使用)
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：状态 1,未删除；2,已删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 1,未删除；2,已删除
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：发行时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：发行时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：卡券种类(0,代金卷;1,折扣券;2,满减券)
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：卡券种类(0,代金卷;1,折扣券;2,满减券)
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：折扣(1至99的整数)
	 */
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	/**
	 * 获取：折扣(1至99的整数)
	 */
	public Integer getDiscount() {
		return discount;
	}
	/**
	 * 设置：满足金额(满减券使用,满金额,满后所减金额用cardmoney保存)
	 */
	public void setFullMoney(Integer fullMoney) {
		this.fullMoney = fullMoney;
	}
	/**
	 * 获取：满足金额(满减券使用,满金额,满后所减金额用cardmoney保存)
	 */
	public Integer getFullMoney() {
		return fullMoney;
	}
	/**
	 * 设置：卡券金额
	 */
	public void setCardMoney(Integer cardMoney) {
		this.cardMoney = cardMoney;
	}
	/**
	 * 获取：卡券金额
	 */
	public Integer getCardMoney() {
		return cardMoney;
	}
	/**
	 * 设置：优惠券名称(如:满100减10;全场8折)
	 */
	public void setCardContent(String cardContent) {
		this.cardContent = cardContent;
	}
	/**
	 * 获取：优惠券名称(如:满100减10;全场8折)
	 */
	public String getCardContent() {
		return cardContent;
	}
}
