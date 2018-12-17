package leiren.haozhaojob.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.community.entity.CommunityMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityMenuDao extends BaseMapper<CommunityMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<CommunityMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<CommunityMenuEntity> queryNotButtonList();

}
