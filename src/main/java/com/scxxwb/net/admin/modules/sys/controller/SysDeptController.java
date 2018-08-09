package com.scxxwb.net.admin.modules.sys.controller;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 部门管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public List<SysDeptEntity> list(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		return deptList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public R select(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		//添加一级部门
		if(getUserId() == Constant.SUPER_ADMIN){
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0);
			root.setName("总公司");
			root.setParentId(-1);
			root.setOpen(true);
			deptList.add(root);
		}

		return R.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public R info(){
		long deptId = 0;
		if(getUserId() != Constant.SUPER_ADMIN){
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
			Integer parentId = null;
			for(SysDeptEntity sysDeptEntity : deptList){
				if(parentId == null){
					parentId = sysDeptEntity.getParentId();
					continue;
				}

				if(parentId > sysDeptEntity.getParentId().longValue()){
					parentId = sysDeptEntity.getParentId();
				}
			}
			deptId = parentId;
		}

		return R.ok().put("deptId", deptId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("deptId") Long deptId){
		SysDeptEntity dept = sysDeptService.selectById(deptId);
		Map map = new HashMap<String, Object>();
		map.put("name",dept.getName());
		List<SysDeptEntity> list = sysDeptService.selectByMap(map);
		return R.ok().put("dept", dept);
	}

    /**
     * 信息
     */
    @RequestMapping("/infoByName/{name}")
    @RequiresPermissions("sys:dept:info")
    public R infoByName(@PathVariable("name") String name){
        Map map = new HashMap<String, Object>();
        map.put("name",name);
        List<SysDeptEntity> list = sysDeptService.selectByMap(map);
        return R.ok().put("list", list);
    }
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:save")
	@SysLog("保存部门")
	public R save(@RequestBody SysDeptEntity dept){
		dept.setUserId(ShiroUtils.getUserEntity().getUserId());
		dept.setCreateTime(new Date());
		ValidatorUtils.validateEntity(dept,AddGroup.class);
		sysDeptService.insert(dept);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
    @SysLog("修改部门")
	public R update(@RequestBody SysDeptEntity dept) {
		dept.setUserId(ShiroUtils.getUserEntity().getUserId());
		dept.setCreateTime(new Date());
		ValidatorUtils.validateEntity(dept, UpdateGroup.class);
		sysDeptService.updateAndForbidBySysDeptEntity(dept);
		return R.ok();
	}

	/**
	 * 判断上级公司是否禁用
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/judgeByStatus/{deptId}")
	@RequiresPermissions("sys:dept:update")
	public R judgeByStatus(@PathVariable("deptId")Integer deptId){
		boolean bool = true;
		if (deptId != 0){
			SysDeptEntity dept = new SysDeptEntity();
			dept = sysDeptService.selectById(deptId);
			if (dept.getStatus() != 1 && dept.getParentId() != 0){
				dept = sysDeptService.selectById(dept.getParentId());
				if (dept.getStatus() == 0){
					bool = false;
				}
			}
			if (dept.getStatus() != 1) {
				bool = false;
			}
		}
		return R.ok().put("bool", bool);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
    @SysLog("删除部门")
	public R delete(Integer deptId){
		//判断是否有子部门
		List<Integer> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return R.error("请先删除子部门");
		}

		sysDeptService.deleteById(deptId);
		
		return R.ok();
	}
}
