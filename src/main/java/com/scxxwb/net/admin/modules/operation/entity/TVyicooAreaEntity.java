package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 14:20:08
 */
@TableName("t_vyicoo_area")
public class TVyicooAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 地区码
	 */
	@TableId
	private Integer areaCode;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 上级区域编码
	 */
	private Integer areaParentId;
	/**
	 * 区域类型
	 */
	private String areaType;

	/**
	 * 设置：地区码
	 */
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取：地区码
	 */
	public Integer getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置：区域名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取：区域名称
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置：上级区域编码
	 */
	public void setAreaParentId(Integer areaParentId) {
		this.areaParentId = areaParentId;
	}
	/**
	 * 获取：上级区域编码
	 */
	public Integer getAreaParentId() {
		return areaParentId;
	}
	/**
	 * 设置：区域类型
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	/**
	 * 获取：区域类型
	 */
	public String getAreaType() {
		return areaType;
	}
}
