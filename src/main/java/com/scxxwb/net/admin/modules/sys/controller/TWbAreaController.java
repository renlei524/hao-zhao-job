package com.scxxwb.net.admin.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.entity.TWbAreaEntity;
import com.scxxwb.net.admin.modules.sys.service.TWbAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.common.utils.R;


/**
 * 地区信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
@RestController
@RequestMapping("sys/twbarea")
public class TWbAreaController {
    @Autowired
    private TWbAreaService tWbAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:twbarea:list")
    public R list(@RequestParam Map<String, Object> params){
        List<TWbAreaEntity> page = tWbAreaService.queryList(new HashMap<String, Object>());
        TWbAreaEntity root = new TWbAreaEntity();
        root.setId(0);
        root.setAreaCode(0);
        root.setName("一级列表");
        root.setParentCode(-1);
        root.setOpen(true);
        page.add(root);
        return R.ok().put("page", page);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:twbarea:select")
    public List<TWbAreaEntity> list(){
        List<TWbAreaEntity> tWbAreaEntityList = tWbAreaService.selectList(null);
        for(TWbAreaEntity tWbAreaEntity : tWbAreaEntityList){
            TWbAreaEntity parentAreaEntity = tWbAreaService.selectById(tWbAreaEntity.getParentCode());
            if(parentAreaEntity != null){
                tWbAreaEntity.setParentName(parentAreaEntity.getName());
            }
        }
        return tWbAreaEntityList;
    }

    @RequestMapping("/infoList/{parentCode}")
    @RequiresPermissions("sys:twbarea:list")
    public List<TWbAreaEntity> infoList(@RequestParam Map<String, Object> params, @PathVariable("parentCode") String parentId){

        List<TWbAreaEntity> tWbAreaEntityList = tWbAreaService.infoList(params,parentId);
        for(TWbAreaEntity tWbAreaEntity : tWbAreaEntityList){
            TWbAreaEntity parentAreaEntity = tWbAreaService.selectByAreaCode(tWbAreaEntity.getParentCode());
            if(parentAreaEntity != null){
                tWbAreaEntity.setParentName(parentAreaEntity.getName());
            }
        }
        return tWbAreaEntityList;
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{areaId}")
    @RequiresPermissions("sys:twbarea:info")
    public R info(@PathVariable("areaId") Integer areaId){
        TWbAreaEntity area = tWbAreaService.selectById(areaId);
        TWbAreaEntity tWbAreaEntity=tWbAreaService.selectById(area.getParentCode());
        if (tWbAreaEntity != null){
            area.setParentName(tWbAreaEntity.getName());
        }
        return R.ok().put("area", area);
    }

    /**
     * 信息
     */
    @RequestMapping("/infoByName/{name}/{parentCode}")
    @RequiresPermissions("sys:twbarea:info")
    public R infoByUserName(@PathVariable("name") String name,@PathVariable("parentCode") String parentCode){
        Map map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("parent_code",parentCode);
        List<TWbAreaEntity> list = tWbAreaService.selectByMap(map);
        return R.ok().put("list", list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:twbarea:save")
    @Transactional(rollbackFor = Exception.class)
    public R save(@RequestBody TWbAreaEntity tWbArea){
        tWbArea.setSort(99);
        ValidatorUtils.validateEntity(tWbArea,AddGroup.class);
        //添加地区code值
        List<TWbAreaEntity> tWbAreaEntitiesList = tWbAreaService.selectList(new EntityWrapper<TWbAreaEntity>().where("parent_code = " + tWbArea.getParentCode()));
        if (!tWbAreaEntitiesList.isEmpty()){
            tWbArea.setAreaCode(tWbAreaEntitiesList.get(tWbAreaEntitiesList.size() - 1).getAreaCode() + 1);
        }else {
            tWbArea.setAreaCode(tWbArea.getParentCode() * 100 + 1);
        }
        tWbAreaService.insertAllColumn(tWbArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:twbarea:update")
    @Transactional(rollbackFor = Exception.class)
    public R update(@RequestParam Map<String, Object> params,@RequestBody TWbAreaEntity tWbArea){
        ValidatorUtils.validateEntity(tWbArea,UpdateGroup.class);
        TWbAreaEntity tWbAreaEntity = tWbAreaService.selectById(tWbArea.getId());
        if (!tWbAreaEntity.getAreaCode().equals(tWbArea.getAreaCode())){
            for (TWbAreaEntity tWbAreaEntitys : tWbAreaService.infoList(params,tWbAreaEntity.getAreaCode().toString())){
                tWbAreaEntitys.setParentCode(tWbArea.getAreaCode());
                tWbAreaService.updateById(tWbAreaEntitys);
            }
        }
        tWbAreaService.updateById(tWbArea);//全部更新

        return R.ok();
    }

    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    @Transactional(rollbackFor = Exception.class)
    public R delete(@RequestParam Map<String, Object> params,String areaId){
        List<TWbAreaEntity> tWbAreaEntities = tWbAreaService.infoList(params, tWbAreaService.selectById(areaId).getAreaCode().toString());

        //判断是否有子菜单或按钮
        if(tWbAreaEntities.size() > 0){
            return R.error("请先删除子菜单或按钮");
        }
        Integer currAreaId = Integer.valueOf(areaId);
        tWbAreaService.deleteById(currAreaId);
        return R.ok();
    }
}
