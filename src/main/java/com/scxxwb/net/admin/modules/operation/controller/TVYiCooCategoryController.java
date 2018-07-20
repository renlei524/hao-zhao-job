package com.scxxwb.net.admin.modules.operation.controller;

import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.modules.operation.entity.TVYiCooCategoryEntity;
import com.scxxwb.net.admin.modules.operation.service.TVYiCooCategoryService;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 经营类目
 *
 */
@RestController
@RequestMapping("operation/tvyicoocategory")
public class TVYiCooCategoryController extends AbstractController {
    @Autowired
    private TVYiCooCategoryService tViYiCooCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:tvyicoocategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tViYiCooCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:tvyicoocategory:info")
    public R info(@PathVariable("id") String id){
         TVYiCooCategoryEntity tvYiCooCategoryEntity = tViYiCooCategoryService.selectById(id);

        return R.ok().put("tvYiCooCategoryEntity", tvYiCooCategoryEntity);
    }
}
