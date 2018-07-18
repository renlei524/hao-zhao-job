package com.scxxwb.net.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface SysMenuService extends IService<SysMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Integer parentId);

	/**
	 * 根据菜单
	 * @param params
	 */
	List<SysMenuEntity> queryList( Map<String, Object> params);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserMenuList(Integer userId);

	/**
	 * 删除
	 */
	void delete(Integer menuId);
}
