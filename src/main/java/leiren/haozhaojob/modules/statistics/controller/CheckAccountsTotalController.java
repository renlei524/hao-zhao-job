package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.entity.CheckAccountsTotalEntity;
import leiren.haozhaojob.modules.statistics.service.CheckAccountsTotalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import leiren.haozhaojob.modules.sys.controller.AbstractController;

/**
 * 财务核账统计
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
@RestController
@RequestMapping("statistics/checkAccountsTotal")
public class CheckAccountsTotalController extends AbstractController {

    @Autowired
    private CheckAccountsTotalService checkAccountsTotalService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:checkAccountsTotal:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = checkAccountsTotalService.queryPage(params);
        return R.ok().put("page",page);
    }
    @RequestMapping("/info")
    @RequiresPermissions("statistics:checkAccountsTotal:info")
    public R info(@RequestParam Map<String, Object> params){
        CheckAccountsTotalEntity checkAccountsTotal = checkAccountsTotalService.selectCheckAccountsByDate(params);
        return R.ok().put("checkAccountsTotal", checkAccountsTotal);
    }
}
