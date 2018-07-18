package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.GoodsEntity;
import com.scxxwb.net.admin.modules.operation.service.GoodsService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
@RestController
@RequestMapping("operation/goods")
public class GoodsController extends AbstractController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:goods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:goods:info")
    public R info(@PathVariable("id") String id){
        GoodsEntity goods = goodsService.selectById(id);

        return R.ok().put("goods", goods);
    }
}
