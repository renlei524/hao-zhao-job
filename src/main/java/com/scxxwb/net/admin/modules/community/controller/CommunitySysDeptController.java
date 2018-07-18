package com.scxxwb.net.admin.modules.community.controller;

import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import com.scxxwb.net.admin.modules.community.entity.CommunitySysDeptEntity;
import com.scxxwb.net.admin.modules.community.service.CommunitySysDeptService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
     * 社区管理
     *
     * @author leiren
     * @email renlei@scxxwb.com
     * @date 2018.05.28
     */
    @RestController
    @RequestMapping("/community/community")
public class CommunitySysDeptController extends AbstractController {
        @Autowired
        private CommunitySysDeptService communitySysDeptService;
        /**
         * 按条件查询所有社区集合
         */
        @RequestMapping("/list")
        @RequiresPermissions("community:community:list")
        public R list(@RequestParam Map<String, Object> params){
            PageUtils page = communitySysDeptService.queryPage(params);

            return R.ok().put("page", page);
        }

    /**
     * 社区管理模块新增时加载社区ztree时使用  d
     * @param params
     * @return
     */
    @RequestMapping("/load")
        @RequiresPermissions("community:community:load")
        public List<CommunitySysDeptEntity> load(@RequestParam Map<String, Object> params){
            List<CommunitySysDeptEntity> deptList = communitySysDeptService.queryList(new HashMap<String, Object>());
            return deptList;
        }

    /**
     * 按id查询社区信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("community:community:info")
    public R info(@PathVariable("id") String communityId){
        CommunitySysDeptEntity community = communitySysDeptService.selectById(communityId);

        return R.ok().put("community", community);
    }
    /**
     * 保存社区信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("community:community:save")
    public R save(@RequestBody CommunitySysDeptEntity communitySysDeptEntity){
       ValidatorUtils.validateEntity(communitySysDeptEntity, AddGroup.class);
       //进行社区名称重复判定
        Map<String, Object> map = new HashMap<>();
        map.put("name", String.valueOf(communitySysDeptEntity.getName()));
        map.put("province", String.valueOf(communitySysDeptEntity.getProvince()));
        map.put("city", String.valueOf(communitySysDeptEntity.getCity()));
        map.put("area", String.valueOf(communitySysDeptEntity.getArea()));
        map.put("town", String.valueOf(communitySysDeptEntity.getTown()));
       List<CommunitySysDeptEntity> list =  communitySysDeptService.selectByMap(map);
       if (list.size() > 0){
           return  R.error("您输入的机构已存在");
       } else {
           //添加创建者id
           communitySysDeptEntity.setCreUserId(Long.valueOf(ShiroUtils.getUserEntity().getUserId()));
           //添加版本号
           String version = (UUID.randomUUID().toString()).replaceAll("-", "");
           communitySysDeptEntity.setVersion(version);
           //添加创建时间
           communitySysDeptEntity.setCreTime(new Date());
           communitySysDeptService.insert(communitySysDeptEntity);
           return R.ok();
       }
    }

    /**
     * 修改社区信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("community:community:update")
    public R update(@RequestBody CommunitySysDeptEntity communitySysDeptEntity){
        ValidatorUtils.validateEntity(communitySysDeptEntity, UpdateGroup.class);
        //进行社区名称重复判定
        Map<String, Object> map = new HashMap<>();
        map.put("name", String.valueOf(communitySysDeptEntity.getName()));
        map.put("province", String.valueOf(communitySysDeptEntity.getProvince()));
        map.put("city", String.valueOf(communitySysDeptEntity.getCity()));
        map.put("area", String.valueOf(communitySysDeptEntity.getArea()));
        map.put("town", String.valueOf(communitySysDeptEntity.getTown()));
        List<CommunitySysDeptEntity> list =  communitySysDeptService.selectByMap(map);
        if (list.size() > 0){
            return  R.error("您输入的机构已存在");
        } else {
            communitySysDeptEntity.setUpdTime(new Date());
            communitySysDeptService.updateById(communitySysDeptEntity);
            return R.ok();
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("community:community:delete")
    public R delete(@RequestBody Long[] deptId){
        communitySysDeptService.deleteBatchIds(Arrays.asList(deptId));

        return R.ok();
    }
}
