package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeTotalEntity;
import leiren.haozhaojob.modules.statistics.service.MerchantIncomeTotalService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商户收入统计
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
@RestController
@RequestMapping("statistics/merchantIncomeTotal")
public class MerchantIncomeTotalController extends AbstractController {

    @Autowired
    MerchantIncomeTotalService merchantIncomeTotalService;

    private  MerchantIncomeTotalEntity merchantIncomeTotalEntity =new MerchantIncomeTotalEntity();

    /**
     * 列表
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:merchantIncomeTotal:list")
    public R list(@RequestParam Map<String, Object> params)
    {
        MerchantIncomeTotalEntity merchantIncomeTotalEntity =merchantIncomeTotalService.queryPage(params);
        this.merchantIncomeTotalEntity = merchantIncomeTotalEntity;
        return R.ok().put("merchantIncomeTotalEntity",merchantIncomeTotalEntity);
    }

    @RequestMapping("/show")
    @RequiresPermissions("statistics:merchantIncomeTotal:list")
    public R show(@RequestParam Map<String, Object> params)
    {
        return R.ok().put("merchantIncomeTotalEntity",this.merchantIncomeTotalEntity);
    }

}
