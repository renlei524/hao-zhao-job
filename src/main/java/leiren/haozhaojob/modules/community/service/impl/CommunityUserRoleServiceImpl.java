package leiren.haozhaojob.modules.community.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.MapUtils;
import leiren.haozhaojob.modules.community.dao.CommunityUserRoleDao;
import leiren.haozhaojob.modules.community.entity.CommunityUserRoleEntity;
import leiren.haozhaojob.modules.community.service.CommunityUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("CommunityUserRoleService")
public class CommunityUserRoleServiceImpl extends ServiceImpl<CommunityUserRoleDao, CommunityUserRoleEntity> implements CommunityUserRoleService {
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		List<CommunityUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			CommunityUserRoleEntity CommunityUserRoleEntity = new CommunityUserRoleEntity();
			CommunityUserRoleEntity.setUserId(userId);
			CommunityUserRoleEntity.setRoleId(roleId);

			list.add(CommunityUserRoleEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
