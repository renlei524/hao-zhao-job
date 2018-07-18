package com.scxxwb.net.admin.modules.community.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.MapUtils;
import com.scxxwb.net.admin.modules.community.dao.CommunityMenuDao;
import com.scxxwb.net.admin.modules.community.entity.CommunityMenuEntity;
import com.scxxwb.net.admin.modules.community.service.CommunityMenuService;
import com.scxxwb.net.admin.modules.community.service.CommunityRoleMenuService;
import com.scxxwb.net.admin.modules.community.service.CommunityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("communityMenuService")
public class CommunityMenuServiceImpl extends ServiceImpl<CommunityMenuDao, CommunityMenuEntity> implements CommunityMenuService {
	@Autowired
	private CommunityUserService communityUserService;
	@Autowired
	private CommunityRoleMenuService communityRoleMenuService;
	@Autowired
	protected KafkaProducer kafkaProducer;
	
	@Override
	public List<CommunityMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<CommunityMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<CommunityMenuEntity> userMenuList = new ArrayList<>();
		for(CommunityMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<CommunityMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<CommunityMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<CommunityMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = communityUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.deleteById(menuId);
		//删除菜单与角色关联
		communityRoleMenuService.deleteByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<CommunityMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<CommunityMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<CommunityMenuEntity> getMenuTreeList(List<CommunityMenuEntity> menuList, List<Long> menuIdList){
		List<CommunityMenuEntity> subMenuList = new ArrayList<CommunityMenuEntity>();
		
		for(CommunityMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
