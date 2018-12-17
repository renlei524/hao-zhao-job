package leiren.haozhaojob.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.modules.sys.dao.SysRoleMenuDao;
import leiren.haozhaojob.modules.sys.entity.SysRoleMenuEntity;
import leiren.haozhaojob.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Integer[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
		for(Integer menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			list.add(sysRoleMenuEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Integer> queryMenuIdList(Integer roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Integer[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
