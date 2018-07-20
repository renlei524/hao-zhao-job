package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.List;
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
    @RequestMapping("/list/{areaCode}")
    @RequiresPermissions("operation:tvyicooarea:list")
    public R list(@PathVariable("areaCode") Integer areaCode){
        List<TVyicooAreaEntity> areaList = tVyicooAreaService.queryPage(areaCode);

        return R.ok().put("areaList", areaList);
    }


}
