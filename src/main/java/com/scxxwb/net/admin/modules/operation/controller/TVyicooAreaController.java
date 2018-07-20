package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.TVyicooAreaEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooAreaService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 14:20:08
 */
@RestController
@RequestMapping("operation/tvyicooarea")
public class TVyicooAreaController {
    @Autowired
    private TVyicooAreaService tVyicooAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:tvyicooarea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tVyicooAreaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{areaCode}")
    @RequiresPermissions("operation:tvyicooarea:info")
    public R info(@PathVariable("areaCode") Integer areaCode){
        TVyicooAreaEntity tVyicooArea = tVyicooAreaService.selectById(areaCode);

        return R.ok().put("tVyicooArea", tVyicooArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:tvyicooarea:save")
    public R save(@RequestBody TVyicooAreaEntity tVyicooArea){
        tVyicooAreaService.insert(tVyicooArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:tvyicooarea:update")
    public R update(@RequestBody TVyicooAreaEntity tVyicooArea){
        ValidatorUtils.validateEntity(tVyicooArea);
        tVyicooAreaService.updateAllColumnById(tVyicooArea);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tvyicooarea:delete")
    public R delete(@RequestBody Integer[] areaCodes){
        tVyicooAreaService.deleteBatchIds(Arrays.asList(areaCodes));

        return R.ok();
    }

}
