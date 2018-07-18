package com.scxxwb.net.admin.modules.sys.controller;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.modules.sys.entity.SysRoleEntity;
import com.scxxwb.net.admin.modules.sys.service.SysRoleDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysRoleMenuService;
import com.scxxwb.net.admin.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		List<SysRoleEntity> list = sysRoleService.selectList(null);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Integer roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);
		
		//查询角色对应的菜单
		List<Integer> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Integer> deptIdList = sysRoleDeptService.queryDeptIdList(new Integer[]{roleId});
		role.setDeptIdList(deptIdList);

		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.save(role);

		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/infoByRoleName/{roleName}")
	@RequiresPermissions("sys:role:info")
	public R infoByRoleName(@PathVariable("roleName") String roleName){
		Map map = new HashMap<String, Object>();
		map.put("role_name",roleName);
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);
		return R.ok().put("list", list);
	}

	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Integer[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
