package leiren.haozhaojob.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.sys.dao.SysRoleDao;
import leiren.haozhaojob.modules.sys.entity.SysRoleEntity;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.shiro.ShiroUtils;
import leiren.haozhaojob.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	@DataFilter(subDept = true, user = true)
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		Page<SysRoleEntity> page = this.selectPage(
			new Query<SysRoleEntity>(params).getPage(),
			new EntityWrapper<SysRoleEntity>()
				.addFilterIfNeed(StringUtils.isNotBlank(roleName),"(role_name like '%" + roleName + "%'"
						+ "or remark like '%" + roleName + "%'"
						+ "or user_id in (select user_id from t_sys_user where real_name like '%" + roleName + "%'))")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,"role_id in (select role_id from t_sys_role_dept where " + params.get(Constant.SQL_FILTER)  + ")")
		);

		for(SysRoleEntity sysRoleEntity : page.getRecords()){
			SysUserEntity sysUserEntity = sysUserService.selectById(sysRoleEntity.getUserId());
			if(sysUserEntity != null){
				sysRoleEntity.setUserName(sysUserEntity.getRealName());
			}
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRoleEntity role) {
		role.setUserId(ShiroUtils.getUserEntity().getUserId());
		role.setCreateTime(new Date());
		this.insert(role);

		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleEntity role) {
		this.updateAllColumnById(role);

		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Integer[] roleIds) {
		//删除角色
		this.deleteBatchIds(Arrays.asList(roleIds));

		//删除角色与菜单关联
		sysRoleMenuService.deleteBatch(roleIds);

		//删除角色与部门关联
		sysRoleDeptService.deleteBatch(roleIds);

		//删除角色与用户关联
		sysUserRoleService.deleteBatch(roleIds);
	}

	@Override
	@DataFilter(subDept = true, user = true)
	public List<SysRoleEntity> selectList(Map<String, Object> params) {
		return this.selectList(new EntityWrapper<SysRoleEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,"role_id in (select role_id from t_sys_role_dept where " + params.get(Constant.SQL_FILTER)  + ")"));
	}
}
