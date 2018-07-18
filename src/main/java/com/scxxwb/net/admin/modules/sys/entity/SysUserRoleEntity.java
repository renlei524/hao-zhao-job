package com.scxxwb.net.admin.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@TableName("t_sys_user_role")
public class SysUserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Integer id;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 设置：
	 * @param id 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * 设置：用户ID
	 * @param userId 用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 * @return Long
	 */
	public Integer getUserId() {
		return userId;
	}
	
	/**
	 * 设置：角色ID
	 * @param roleId 角色ID
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：角色ID
	 * @return Long
	 */
	public Integer getRoleId() {
		return roleId;
	}
	
}
