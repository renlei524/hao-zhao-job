package leiren.haozhaojob.modules.operation.controller;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.operation.service.WbOrderFreeService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 订单数据表0
 */
@RestController
@RequestMapping("operation/twborderfree")
public class WbOrderFreeController extends AbstractController {
    @Autowired
    private WbOrderFreeService wbOrderFreeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twborderfree:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wbOrderFreeService.selectTWBOrderFreeBySearchCondition(params);

        return R.ok().put("page", page);
    }
}
