package com.scxxwb.net.admin.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.community.entity.CommunityUserEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityUserDao extends BaseMapper<CommunityUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	public Integer getUser_count(Map map) throws Exception;
	
	public List<CommunityUserEntity> getUser(Map map) throws Exception;

}
