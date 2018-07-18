package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
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
@TableName("t_sku_goods")
public class SkuGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品名称
	 */
	@NotBlank(message = "商品名称不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String goodsName;
	/**
	 * 商品详细
	 */
	@NotBlank(message = "商品详情不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String details;
	/**
	 * 状态1为正常 2为暂停销售 3删除
	 */
	private Integer status;
	/**
	 * 商品图片
	 */
	private String pictures;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 创建人
	 */
	private Integer userId;
	/**
	 * 创建人姓名
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 * 备注
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
	 * 设置：开始时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建人
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getUserId() {
		return userId;
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
	/**
	 * 获取创建人姓名
	 * @author yuhuan
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置创建人姓名
	 * @author yuhuan
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
