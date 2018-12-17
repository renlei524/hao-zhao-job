package leiren.haozhaojob.modules.operation.controller;

import java.util.Map;

import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import leiren.haozhaojob.modules.operation.entity.WbOrderEntity;
import leiren.haozhaojob.modules.operation.service.WbOrderService;


/**
 * 订单数据表0
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@RestController
@RequestMapping("operation/twborder")
public class WbOrderController extends AbstractController {
    @Autowired
    private WbOrderService tWbOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twborder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tWbOrderService.selectByUserName(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list_merchant")
    @RequiresPermissions("operation:twborder:list")
    public R lists(@RequestParam Map<String, Object> params){
        PageUtils page = tWbOrderService.selectByMerchantId(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("operation:twborder:info")
    public R info(@PathVariable("orderId") String orderId){
        WbOrderEntity tWbOrder = tWbOrderService.selectById(orderId);

        return R.ok().put("tWbOrder", tWbOrder);
    }

    /**
     * yoghurtid
     * @return
     */
    @RequestMapping("/bills")
    @RequiresPermissions("operation:twborder:bills")
    public R billsInfo(@RequestParam Map<String, Object> params){
        PageUtils page = tWbOrderService.selectWBOrder(params);

        return R.ok().put("page", page);
    }

}
