package leiren.haozhaojob.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.sys.entity.WbAreaEntity;
import leiren.haozhaojob.modules.sys.service.WbAreaService;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 地区信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
@RestController
@RequestMapping("sys/twbarea")
public class WbAreaController extends AbstractController{
    @Autowired
    private WbAreaService tWbAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:twbarea:list")
    public R list(@RequestParam Map<String, Object> params){
        List<WbAreaEntity> page = tWbAreaService.queryList(new HashMap<String, Object>());
        WbAreaEntity root = new WbAreaEntity();
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
    public List<WbAreaEntity> list(){
        List<WbAreaEntity> tWbAreaEntityList = tWbAreaService.selectList(null);
        for(WbAreaEntity tWbAreaEntity : tWbAreaEntityList){
            WbAreaEntity parentAreaEntity = tWbAreaService.selectById(tWbAreaEntity.getParentCode());
            if(parentAreaEntity != null){
                tWbAreaEntity.setParentName(parentAreaEntity.getName());
            }
        }
        return tWbAreaEntityList;
    }

    @RequestMapping("/infoList/{parentCode}")
    @RequiresPermissions("sys:twbarea:list")
    public List<WbAreaEntity> infoList(@RequestParam Map<String, Object> params, @PathVariable("parentCode") String parentId){

        List<WbAreaEntity> tWbAreaEntityList = tWbAreaService.infoList(params,parentId);
        for(WbAreaEntity tWbAreaEntity : tWbAreaEntityList){
            WbAreaEntity parentAreaEntity = tWbAreaService.selectByAreaCode(tWbAreaEntity.getParentCode());
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
        WbAreaEntity area = tWbAreaService.selectById(areaId);
        WbAreaEntity tWbAreaEntity=tWbAreaService.selectById(area.getParentCode());
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
        List<WbAreaEntity> list = tWbAreaService.selectByMap(map);
        return R.ok().put("list", list);
    }

    /**
     * 保存
     */
    @SysLog("保存地区")
    @RequestMapping("/save")
    @RequiresPermissions("sys:twbarea:save")
    @Transactional(rollbackFor = Exception.class)
    public R save(@RequestBody WbAreaEntity tWbArea){
        tWbArea.setSort(99);
        ValidatorUtils.validateEntity(tWbArea,AddGroup.class);
        //添加地区code值
        List<WbAreaEntity> tWbAreaEntitiesList = tWbAreaService.selectList(new EntityWrapper<WbAreaEntity>().where("parent_code = " + tWbArea.getParentCode()));
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
    @SysLog("修改地区")
    @RequestMapping("/update")
    @RequiresPermissions("sys:twbarea:update")
    @Transactional(rollbackFor = Exception.class)
    public R update(@RequestParam Map<String, Object> params,@RequestBody WbAreaEntity tWbArea){
        ValidatorUtils.validateEntity(tWbArea,UpdateGroup.class);
        tWbAreaService.updateAreaByTWBArea(params, tWbArea);
        return R.ok();
    }

    /**
     * 删除地区
     * @param params
     * @param areaId
     * @return
     */
    @SysLog("删除地区")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    @Transactional(rollbackFor = Exception.class)
    public R delete(@RequestParam Map<String, Object> params,String areaId){
        List<WbAreaEntity> tWbAreaEntities = tWbAreaService.infoList(params, tWbAreaService.selectById(areaId).getAreaCode().toString());

        //判断是否有子菜单或按钮
        if(tWbAreaEntities.size() > 0){
            return R.error("请先删除子菜单或按钮");
        }
        Integer currAreaId = Integer.valueOf(areaId);
        tWbAreaService.deleteById(currAreaId);
        return R.ok();
    }
}
