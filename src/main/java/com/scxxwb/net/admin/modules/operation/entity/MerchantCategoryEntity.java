package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-11 17:06:19
 */
@TableName("t_merchant_category")
public class MerchantCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	private Integer categoryId;
	/**
	 * 分类父id
	 */
	@NotNull(message = "上级店铺分类名不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Integer parentId;

	/**
	 * 分类父名称
	 */
	@TableField(exist=false)
	private String parentName;
	/**
	 * 分类名
	 */
	@NotNull(message = "店铺分类名不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 分类图片
	 */
	private String categoryImg;
	/**
	 * 
	 */
	private Integer sort;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;

	/**
	 * 设置：分类id
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：分类id
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：分类父id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：分类父id
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：分类名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：分类名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：分类图片
	 */
	public void setCategoryImg(String categoryImg) {
		this.categoryImg = categoryImg;
	}
	/**
	 * 获取：分类图片
	 */
	public String getCategoryImg() {
		return categoryImg;
	}
	/**
	 * 设置：
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：
	 */
	public Integer getSort() {
		return sort;
	}


	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
