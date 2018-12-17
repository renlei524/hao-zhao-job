package leiren.haozhaojob.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.modules.community.entity.CommunityMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityMenuService extends IService<CommunityMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<CommunityMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<CommunityMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<CommunityMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<CommunityMenuEntity> getUserMenuList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long menuId);
}
