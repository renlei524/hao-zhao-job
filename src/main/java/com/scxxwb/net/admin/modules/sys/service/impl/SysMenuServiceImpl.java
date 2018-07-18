package com.scxxwb.net.admin.modules.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.MapUtils;
import com.scxxwb.net.admin.modules.sys.dao.SysMenuDao;
import com.scxxwb.net.admin.modules.sys.entity.SysMenuEntity;
import com.scxxwb.net.admin.modules.sys.service.SysMenuService;
import com.scxxwb.net.admin.modules.sys.service.SysRoleMenuService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	protected KafkaProducer kafkaProducer;
	
	@Override
	public List<SysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Integer parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<SysMenuEntity> queryList(Map<String, Object> params) {
		List<SysMenuEntity> sysMenuEntityList = this.selectList(new EntityWrapper<SysMenuEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,"menu_id in (select menu_id from t_sys_role_menu where role_id in (select role_id from t_sys_role_dept where " + params.get(Constant.SQL_FILTER)  + "))"));
		return sysMenuEntityList;
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Integer userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Integer> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Integer menuId){
		//删除菜单
		this.deleteById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.deleteByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Integer> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Integer> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<>();
		
		for(SysMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
