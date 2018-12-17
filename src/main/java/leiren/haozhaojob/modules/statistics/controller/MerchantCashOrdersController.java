package leiren.haozhaojob.modules.statistics.controller;

import java.util.Arrays;
import java.util.Map;

import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import leiren.haozhaojob.modules.statistics.entity.MerchantCashOrdersEntity;
import leiren.haozhaojob.modules.statistics.service.MerchantCashOrdersService;


/**
 * 商家提现打包记录订单表
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-14 09:27:44
 */
@RestController
@RequestMapping("statistics/tmerchantcashorders")
public class MerchantCashOrdersController extends AbstractController {
    @Autowired
    private MerchantCashOrdersService tMerchantCashOrdersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:tmerchantcashorders:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tMerchantCashOrdersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cashOrderId}")
    @RequiresPermissions("statistics:tmerchantcashorders:info")
    public R info(@PathVariable("cashOrderId") Long cashOrderId){
        MerchantCashOrdersEntity tMerchantCashOrders = tMerchantCashOrdersService.selectById(cashOrderId);

        return R.ok().put("tMerchantCashOrders", tMerchantCashOrders);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("statistics:tmerchantcashorders:save")
    public R save(@RequestBody MerchantCashOrdersEntity tMerchantCashOrders){
        tMerchantCashOrdersService.insert(tMerchantCashOrders);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("statistics:tmerchantcashorders:update")
    public R update(@RequestBody MerchantCashOrdersEntity tMerchantCashOrders){
        ValidatorUtils.validateEntity(tMerchantCashOrders);
        tMerchantCashOrdersService.updateAllColumnById(tMerchantCashOrders);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("statistics:tmerchantcashorders:delete")
    public R delete(@RequestBody Long[] cashOrderIds){
        tMerchantCashOrdersService.deleteBatchIds(Arrays.asList(cashOrderIds));

        return R.ok();
    }

}
