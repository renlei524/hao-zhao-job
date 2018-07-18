package com.scxxwb.net.admin.modules.community.controller;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleEntity;
import com.scxxwb.net.admin.modules.community.service.CommunityRoleDeptService;
import com.scxxwb.net.admin.modules.community.service.CommunityRoleMenuService;
import com.scxxwb.net.admin.modules.community.service.CommunityRoleService;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/community/role")
public class CommunityRoleController extends AbstractController {
    @Autowired
    private CommunityRoleService communityRoleService;
    @Autowired
    private CommunityRoleMenuService communityRoleMenuService;
    @Autowired
    private CommunityRoleDeptService communityRoleDeptService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("community:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = communityRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("community:role:select")
    public R select(){
        List<CommunityRoleEntity> list = communityRoleService.selectList(null);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("community:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        CommunityRoleEntity role = communityRoleService.selectById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = communityRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = communityRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("community:role:save")
    public R save(@RequestBody CommunityRoleEntity role){
        ValidatorUtils.validateEntity(role,AddGroup.class);

        communityRoleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("community:role:update")
    public R update(@RequestBody CommunityRoleEntity role){
        ValidatorUtils.validateEntity(role,UpdateGroup.class);

        communityRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("community:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        communityRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
