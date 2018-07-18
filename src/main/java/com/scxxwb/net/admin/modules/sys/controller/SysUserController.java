package com.scxxwb.net.admin.modules.sys.controller;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.Assert;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserRoleService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Integer userId){
		SysUserEntity user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		user.setPassword(user.getPassword());
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/infoByUserName/{userName}")
	@RequiresPermissions("sys:user:info")
	public R infoByUserName(@PathVariable("userName") String userName){
		Map map = new HashMap<String, Object>();
		map.put("user_name",userName);
		List<SysUserEntity> list = sysUserService.selectByMap(map);
		return R.ok().put("list", list);
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));
		
		return R.ok();
	}

	/**
	 * 判断所属公司是否禁用
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/judgeDeptByStatus/{deptId}")
	@RequiresPermissions("sys:user:info")
	public R judgeDeptByStatus(@PathVariable("deptId")Integer deptId){
		boolean bool = true;
		SysDeptEntity sysDeptEntity = sysDeptService.selectById(deptId);
		if (sysDeptEntity != null){
			if (sysDeptEntity.getStatus() != 1){
				bool = false;
			}
		}
		return R.ok().put("bool", bool);
	}

}
