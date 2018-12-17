package leiren.haozhaojob.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.modules.community.entity.CommunityUserRoleEntity;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityUserRoleService extends IService<CommunityUserRoleEntity> {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
