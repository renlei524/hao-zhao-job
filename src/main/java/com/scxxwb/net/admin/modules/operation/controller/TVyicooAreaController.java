package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scxxwb.net.admin.modules.operation.entity.TVyicooAreaEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooAreaService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;

import static com.qcloud.cos.http.RequestHeaderValue.Method.POST;


/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 14:20:08
 */
@Api(value = "operation/tvyicooarea", tags = "地区管理")
@RestController
@RequestMapping("operation/tvyicooarea")
public class TVyicooAreaController {
    @Autowired
    private TVyicooAreaService tVyicooAreaService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list/{areaCode}")
    @RequiresPermissions("operation:tvyicooarea:list")
    @ApiOperation(value = "根据地区编号显示起下级地区", httpMethod = POST)
    public R list(@ApiParam(value = "地区编码" , required = true) @PathVariable("areaCode") Integer areaCode){
        List<TVyicooAreaEntity> areaList = tVyicooAreaService.queryPage(areaCode);

        return R.ok().put("areaList", areaList);
    }
}
