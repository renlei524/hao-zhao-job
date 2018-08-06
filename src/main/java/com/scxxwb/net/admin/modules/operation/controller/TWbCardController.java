package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.scxxwb.net.admin.common.annotation.SysLog;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.TWbCardEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbCardService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 卡券表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
@RestController
@RequestMapping("operation/twbcard")
public class TWbCardController extends AbstractController {
    @Autowired
    private TWbCardService tWbCardService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twbcard:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tWbCardService.queryPage(params);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:twbcard:info")
    public R info(@PathVariable("id") String id){
        TWbCardEntity tWbCard = tWbCardService.selectById(id);
        return R.ok().put("tWbCard", tWbCard);
    }

    /**
     * 保存
     */
    @SysLog("保存优惠卷")
    @RequestMapping("/save")
    @RequiresPermissions("operation:twbcard:save")
    public R save(@RequestBody TWbCardEntity tWbCard){
        tWbCard.setCreatorId(ShiroUtils.getUserEntity().getUserId());
        ValidatorUtils.validateEntity(tWbCard,AddGroup.class);
        tWbCardService.insert(tWbCard);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改优惠卷")
    @RequestMapping("/update")
    @RequiresPermissions("operation:twbcard:update")
    public R update(@RequestBody TWbCardEntity tWbCard){
        ValidatorUtils.validateEntity(tWbCard,UpdateGroup.class);
        tWbCardService.updateAllColumnById(tWbCard);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除优惠卷")
    @RequestMapping("/delete")
    @RequiresPermissions("operation:twbcard:delete")
    public R delete(@RequestBody String[] ids){
        tWbCardService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
