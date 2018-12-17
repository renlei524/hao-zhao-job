package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.service.MerchantIncomeDetailService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
@RestController
@RequestMapping("statistics/merchantIncomeDetail")
public class MerchantIncomeDetailController extends AbstractController {

    @Autowired
    private MerchantIncomeDetailService merchantIncomeDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:merchantIncomeDetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = merchantIncomeDetailService.queryPage(params);
        return R.ok().put("page",page);
    }

}
