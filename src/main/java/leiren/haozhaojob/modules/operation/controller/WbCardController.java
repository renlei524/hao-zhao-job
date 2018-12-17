package leiren.haozhaojob.modules.operation.controller;

import java.util.Arrays;
import java.util.Map;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.operation.entity.WbCardEntity;
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

import leiren.haozhaojob.modules.operation.service.WbCardService;


/**
 * 卡券表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
@RestController
@RequestMapping("operation/twbcard")
public class WbCardController extends AbstractController {
    @Autowired
    private WbCardService wbCardService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twbcard:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wbCardService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:twbcard:info")
    public R info(@PathVariable("id") String id){
        WbCardEntity tWbCard = wbCardService.selectById(id);
        return R.ok().put("tWbCard", tWbCard);
    }

    /**
     * 保存
     */
    @SysLog("保存优惠卷")
    @RequestMapping("/save")
    @RequiresPermissions("operation:twbcard:save")
    public R save(@RequestBody WbCardEntity tWbCard){
        tWbCard.setCreatorId(0); // 0表示平台发放
        ValidatorUtils.validateEntity(tWbCard, AddGroup.class);
        wbCardService.insert(tWbCard);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改优惠卷")
    @RequestMapping("/update")
    @RequiresPermissions("operation:twbcard:update")
    public R update(@RequestBody WbCardEntity tWbCard){
        ValidatorUtils.validateEntity(tWbCard, UpdateGroup.class);
        wbCardService.updateAllColumnById(tWbCard);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除优惠卷")
    @RequestMapping("/delete")
    @RequiresPermissions("operation:twbcard:delete")
    public R delete(@RequestBody String[] ids){
        wbCardService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 禁用
     */
    @SysLog("禁用优惠券")
    @RequestMapping("/stop")
    @RequiresPermissions("operation:twbcard:stop")
    public R stop(@RequestBody Integer[] ids){
        wbCardService.stopWBCard(ids);

        return R.ok();
    }
}
