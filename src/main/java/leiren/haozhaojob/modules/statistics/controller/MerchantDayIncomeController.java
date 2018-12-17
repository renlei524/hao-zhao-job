package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.service.MerchantDayIncomeService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商户日收入
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.07.12
 */
@RestController
@RequestMapping("statistics/merchantDayIncome")
public class MerchantDayIncomeController extends AbstractController {

    @Autowired
    private MerchantDayIncomeService merchantDayIncomeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:merchantDayIncome:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = merchantDayIncomeService.queryPage(params);
        return R.ok().put("page",page);
    }

}
