package com.scxxwb.net.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * 系统用户
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Integer userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Integer> queryAllMenuId(Integer userId);

	int updateUserByDeptId(List<Integer> arr);

}
