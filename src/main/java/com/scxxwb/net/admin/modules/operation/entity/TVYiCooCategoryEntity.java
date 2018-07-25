package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 经营类目
 * 
 */
@TableName("t_vyicoo_category")
public class TVYiCooCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 经营类目id
	 */
	private Integer id;
	/**
	 * 经营类目
	 */
	private String category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
