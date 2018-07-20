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

import com.scxxwb.net.admin.modules.operation.entity.TVyicooJinjianEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooJinjianService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
@RestController
@RequestMapping("operation/tvyicoojinjian")
public class TVyicooJinjianController {
    @Autowired
    private TVyicooJinjianService tVyicooJinjianService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:tvyicoojinjian:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tVyicooJinjianService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{type}")
    @RequiresPermissions("operation:tvyicoojinjian:info")
    public R info(@PathVariable("type") String type){
        TVyicooJinjianEntity tVyicooJinjian = tVyicooJinjianService.selectById(type);

        return R.ok().put("tVyicooJinjian", tVyicooJinjian);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:tvyicoojinjian:save")
    public R save(@RequestBody TVyicooJinjianEntity tVyicooJinjian){
        tVyicooJinjianService.insert(tVyicooJinjian);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:tvyicoojinjian:update")
    public R update(@RequestBody TVyicooJinjianEntity tVyicooJinjian){
        ValidatorUtils.validateEntity(tVyicooJinjian);
        tVyicooJinjianService.updateAllColumnById(tVyicooJinjian);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tvyicoojinjian:delete")
    public R delete(@RequestBody String[] types){
        tVyicooJinjianService.deleteBatchIds(Arrays.asList(types));

        return R.ok();
    }

}
