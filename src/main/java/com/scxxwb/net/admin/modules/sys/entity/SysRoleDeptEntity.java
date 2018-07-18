package com.scxxwb.net.admin.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@TableName("t_sys_role_dept")
public class SysRoleDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Integer id;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 部门ID
	 */
	private Integer deptId;

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
	
	/**
	 * 设置：部门ID
	 * @param deptId 部门ID
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取：部门ID
	 * @return Long
	 */
	public Integer getDeptId() {
		return deptId;
	}
	
}
