package com.scxxwb.net.admin.modules.community.controller;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.Assert;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.community.entity.CommunityUserEntity;
import com.scxxwb.net.admin.modules.community.service.CommunityUserRoleService;
import com.scxxwb.net.admin.modules.community.service.CommunityUserService;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysUserRoleService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@RequestMapping("/community/user")
public class CommunityUserController extends AbstractController {
	@Autowired
	private CommunityUserService communityUserService;
	@Autowired
	private CommunityUserRoleService communityUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("community:user:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = communityUserService.queryPage(params);

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
	@SysLog("修改社区密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = communityUserService.updatePassword(Long.valueOf(getUserId()), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("community:user:info")
	public R info(@PathVariable("userId") Long userId){
		CommunityUserEntity user = communityUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = communityUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存社区用户")
	@RequestMapping("/save")
	@RequiresPermissions("community:user:save")
	public R save(@RequestBody CommunityUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);

		communityUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改社区用户")
	@RequestMapping("/update")
	@RequiresPermissions("community:user:update")
	public R update(@RequestBody CommunityUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		communityUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除社区用户")
	@RequestMapping("/delete")
	@RequiresPermissions("community:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}

		communityUserService.deleteBatchIds(Arrays.asList(userIds));
		
		return R.ok();
	}
}
