package leiren.haozhaojob.modules.operation.controller;

import java.util.List;

import leiren.haozhaojob.modules.sys.controller.AbstractController;
import io.swagger.annotations.*;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import leiren.haozhaojob.modules.operation.entity.VyicooAreaEntity;
import leiren.haozhaojob.modules.operation.service.VyicooAreaService;

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
public class VyicooAreaController extends AbstractController {
    @Autowired
    private VyicooAreaService tVyicooAreaService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list/{areaCode}")
    @RequiresPermissions("operation:tvyicooarea:list")
    @ApiOperation(value = "根据地区编号显示起下级地区", httpMethod = POST)
    public R list(@ApiParam(value = "地区编码" , required = true) @PathVariable("areaCode") Integer areaCode){
        List<VyicooAreaEntity> areaList = tVyicooAreaService.queryPage(areaCode);

        return R.ok().put("areaList", areaList);
    }
}
