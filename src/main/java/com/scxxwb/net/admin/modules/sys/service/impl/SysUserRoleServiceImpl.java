package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.MapUtils;
import com.scxxwb.net.admin.modules.sys.dao.SysUserRoleDao;
import com.scxxwb.net.admin.modules.sys.entity.SysUserRoleEntity;
import com.scxxwb.net.admin.modules.sys.service.SysUserRoleService;
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
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
	@Autowired
	protected KafkaProducer kafkaProducer;
	@Override
	public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(Integer roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Integer> queryRoleIdList(Integer userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Integer[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
