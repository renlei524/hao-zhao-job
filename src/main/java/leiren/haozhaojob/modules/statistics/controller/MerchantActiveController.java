package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.service.MerchantActiveService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 活跃商户
 *
 */
@RestController
@RequestMapping("statistics/merchantActive")
public class MerchantActiveController extends AbstractController {

    @Autowired
    private MerchantActiveService merchantActiveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:merchantActive:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = merchantActiveService.queryPage(params);
        return R.ok().put("page",page);
    }

}
