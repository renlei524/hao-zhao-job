package leiren.haozhaojob.modules.operation.controller;

import java.util.Map;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import leiren.haozhaojob.modules.operation.entity.WbOrderDetailsEntity;
import leiren.haozhaojob.modules.operation.service.WbOrderDetailsService;


/**
 * 订单明细数据表0
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-29 14:54:25
 */
@RestController
@RequestMapping("operation/wborderdetails")
public class WbOrderDetailsController {
    @Autowired
    private WbOrderDetailsService wbOrderDetailsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twborderdetails:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wbOrderDetailsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:twborderdetails:info")
    public R info(@PathVariable("id") Integer id){
        WbOrderDetailsEntity tWbOrderDetails = wbOrderDetailsService.selectById(id);

        return R.ok().put("tWbOrderDetails", tWbOrderDetails);
    }
}
