package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import com.scxxwb.net.admin.modules.sys.entity.SysRoleEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.SkuGoodsEntity;
import com.scxxwb.net.admin.modules.operation.service.SkuGoodsService;
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
@RequestMapping("operation/skuGoods")
public class SkuGoodsController extends AbstractController {
    @Autowired
    private SkuGoodsService skuGoodsService;
    /**
     * @author yuhuan
     */
    @Autowired
    private SysUserService sysUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:skuGoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuGoodsService.queryPage(params);
        for (int i = 0 ; i < page.getList().size(); i ++) {
            Integer userId = ((SkuGoodsEntity) page.getList().get(i)).getUserId();
            SysUserEntity sysUserEntity = sysUserService.selectById(userId);
            if (sysUserEntity != null){
                ((SkuGoodsEntity) page.getList().get(i)).setUserName(sysUserEntity.getRealName());
            }
        }

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:skuGoods:info")
    public R info(@PathVariable("id") String id){
        SkuGoodsEntity skuGoods = skuGoodsService.selectById(id);

        return R.ok().put("skuGoods", skuGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:skuGoods:save")
    public R save(@RequestBody SkuGoodsEntity skuGoods){
        //设置添加商品时间
        skuGoods.setCreateTime(new Date());
        //添加商品创始人
        skuGoods.setUserId(ShiroUtils.getUserEntity().getUserId());
        ValidatorUtils.validateEntity(skuGoods,AddGroup.class);
        skuGoodsService.insert(skuGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:skuGoods:update")
    public R update(@RequestBody SkuGoodsEntity skuGoods){
        ValidatorUtils.validateEntity(skuGoods,UpdateGroup.class);
        skuGoodsService.updateAllColumnById(skuGoods);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:skuGoods:delete")
    public R delete(@RequestBody String[] ids){
        skuGoodsService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
