package com.scxxwb.net.admin.modules.operation.controller;

import java.util.HashMap;
import java.util.List;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.MerchantCategoryEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantCategoryService;
import com.scxxwb.net.admin.common.utils.R;

import static com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils.getUserId;


/**
 * 
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-11 17:06:19
 */
@RestController
@RequestMapping("sys/merchantcategory")
public class MerchantCategoryController {
    @Autowired
    private MerchantCategoryService merchantCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:merchantcategory:list")
    public List<MerchantCategoryEntity> list() {
        List<MerchantCategoryEntity> merchantCategoryList = merchantCategoryService.queryList(new HashMap<String, Object>());
        return merchantCategoryList;
    }

    @RequestMapping("/select")
    @RequiresPermissions("sys:merchantcategory:select")
    public List<MerchantCategoryEntity> select(){
        List<MerchantCategoryEntity> merchantCategoryList = merchantCategoryService.queryList(new HashMap<String, Object>());
        if (getUserId() == Constant.SUPER_ADMIN){
            MerchantCategoryEntity root =new MerchantCategoryEntity();
            root.setCategoryId(0);
            root.setName("一级店铺分类");
            root.setParentId(-1);
            root.setOpen(true);
            merchantCategoryList.add(root);
        }
        return merchantCategoryList;
    }

    /**
     * 上级店铺分类Id(一级店铺则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:merchantcategory:list")
    public R info(){
        long categoryId = 0;
        if(getUserId() != Constant.SUPER_ADMIN){
            List<MerchantCategoryEntity> merchantCategoryList = merchantCategoryService.queryList(new HashMap<String, Object>());
            Integer parentId = null;
            for(MerchantCategoryEntity merchantCategoryEntity : merchantCategoryList){
                if(parentId == null){
                    parentId = merchantCategoryEntity.getParentId();
                    continue;
                }
                if(parentId > merchantCategoryEntity.getParentId().longValue()){
                    parentId = merchantCategoryEntity.getParentId();
                }
            }
            categoryId = parentId;
        }

        return R.ok().put("categoryId", categoryId);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{categoryId}")
    @RequiresPermissions("sys:merchantcategory:info")
    public R info(@PathVariable("categoryId") String categoryId){
        MerchantCategoryEntity merchantCategory = merchantCategoryService.selectById(categoryId);

        return R.ok().put("merchantCategory", merchantCategory);
    }

    /**
     * 保存
     */
    @SysLog("保存商户类型")
    @RequestMapping("/save")
    @RequiresPermissions("sys:merchantcategory:save")
    public R save(@RequestBody MerchantCategoryEntity merchantCategory){
        ValidatorUtils.validateEntity(merchantCategory,AddGroup.class);
        merchantCategoryService.insert(merchantCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改商户类型")
    @RequestMapping("/update")
    @RequiresPermissions("sys:merchantcategory:update")
    public R update(@RequestBody MerchantCategoryEntity merchantCategory){
        ValidatorUtils.validateEntity(merchantCategory,UpdateGroup.class);
        merchantCategoryService.updateAllColumnById(merchantCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除商户类型")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:merchantcategory:delete")
    public R delete(long categoryId){
        //判断是否有子店铺分类
        List<Long> merchantCategoryList = merchantCategoryService.queryMerchantCategoryIdList(categoryId);
        if(merchantCategoryList.size() > 0){
            return R.error("请先删除子店铺分类");
        }

        merchantCategoryService.deleteById(categoryId);

        return R.ok();
    }

}
