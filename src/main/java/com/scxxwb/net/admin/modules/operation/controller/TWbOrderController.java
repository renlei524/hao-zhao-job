package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbOrderService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 订单数据表0
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@RestController
@RequestMapping("operation/twborder")
public class TWbOrderController extends AbstractController {
    @Autowired
    private TWbOrderService tWbOrderService;

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
        TWbOrderEntity tWbOrder = tWbOrderService.selectById(orderId);

        return R.ok().put("tWbOrder", tWbOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:twborder:save")
    public R save(@RequestBody TWbOrderEntity tWbOrder){
        ValidatorUtils.validateEntity(tWbOrder,AddGroup.class);
        tWbOrderService.insert(tWbOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:twborder:update")
    public R update(@RequestBody TWbOrderEntity tWbOrder){
        ValidatorUtils.validateEntity(tWbOrder,UpdateGroup.class);
        tWbOrderService.updateAllColumnById(tWbOrder);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:twborder:delete")
    public R delete(@RequestBody String[] orderIds){
        tWbOrderService.deleteBatchIds(Arrays.asList(orderIds));

        return R.ok();
    }

}
