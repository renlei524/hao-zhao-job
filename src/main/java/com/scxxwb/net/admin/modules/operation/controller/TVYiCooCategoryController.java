package com.scxxwb.net.admin.modules.operation.controller;

import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.modules.operation.entity.TVYiCooCategoryEntity;
import com.scxxwb.net.admin.modules.operation.service.TVYiCooCategoryService;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

import static com.qcloud.cos.http.RequestHeaderValue.Method.POST;


/**
 * 微易客经营类目
 *
 */
@Api(value = "operation/tvyicoocategory", tags = "微易客经营类目")
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
    @ApiOperation(value = "列表", httpMethod = POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNum", required = true, value = "当前页码"),
//    })
    @ApiImplicitParam(name = "params", value = "查询信息", required = true, dataType = "json")
    public R list(@ApiIgnore() @RequestParam Map<String, Object> params){
        PageUtils page = tViYiCooCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:tvyicoocategory:info")
    @ApiOperation(value = "根据id查询微易客经营类目信息", httpMethod = POST)
        public R info(
            @ApiParam(value = "经营类目id", required=true) @RequestParam Integer id
        ){
         TVYiCooCategoryEntity tvYiCooCategoryEntity = tViYiCooCategoryService.selectById(id);

        return R.ok().put("tvYiCooCategoryEntity", tvYiCooCategoryEntity);
    }

}
