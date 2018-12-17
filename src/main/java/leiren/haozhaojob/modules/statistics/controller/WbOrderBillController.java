package leiren.haozhaojob.modules.statistics.controller;

import java.util.Map;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import leiren.haozhaojob.modules.statistics.service.WbOrderBillService;


/**
 * 结算单据表0
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-17 14:11:41
 */
@RestController
@RequestMapping("statistics/wborderbill")
public class WbOrderBillController {
    @Autowired
    private WbOrderBillService wbOrderBillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:wborderbill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wbOrderBillService.queryPage(params);

        return R.ok().put("page", page);
    }

}
