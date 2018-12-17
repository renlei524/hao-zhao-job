package leiren.haozhaojob.modules.operation.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
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

import leiren.haozhaojob.modules.operation.service.EnchashmentBindingService;


/**
 * 商户与用户的提现账户绑定
 *
 * @author liyun
 * @email 2563720820@qq.com
 * @date 2018-07-10 10:10:11
 */
@RestController
@RequestMapping("operation/tenchashmentbinding")
public class EnchashmentBindingController extends AbstractController {
    @Autowired
    private EnchashmentBindingService tEnchashmentBindingService;

    @Autowired
    MerchantService merchantService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:tenchashmentbinding:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tEnchashmentBindingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:tenchashmentbinding:info")
    public R info(@PathVariable("id") Integer id){
        EnchashmentBindingEntity tEnchashmentBinding = tEnchashmentBindingService.selectById(id);
        //添加商户名称
        MerchantEntity merchantEntity =merchantService.selectById(tEnchashmentBinding.getObjectId());
        if (merchantEntity != null){
            tEnchashmentBinding.setObjectName(merchantEntity.getMerchantName());
        }
//        Map map = new HashMap<String, Object>();
//        map.put("code",tEnchashmentBinding.getSubbranch());
//        List<SysDictEntity> list = sysDictService.selectByMap(map);
//        if (list.size() != 0){
//            tEnchashmentBinding.setSubbranchName(list.get(0).getValue());
//        }
        return R.ok().put("tEnchashmentBinding", tEnchashmentBinding);
    }

    /**
     * 信息
     */
    private List<EnchashmentBindingEntity> infoByMerchantId(Integer merchantId){
        Map map = new HashMap<String, Object>();
        map.put("object_id",merchantId);
        List<EnchashmentBindingEntity> list = tEnchashmentBindingService.selectByMap(map);
        return list;
    }

    /**
     * 保存
     */
    @SysLog("保存银行卡")
    @RequestMapping("/save")
    @RequiresPermissions("operation:tenchashmentbinding:save")
    public R save(@RequestBody EnchashmentBindingEntity tEnchashmentBinding){
        //修改银行卡状态为创建审核中
        tEnchashmentBinding.setStatus(2);
        tEnchashmentBinding.setRealName(tEnchashmentBinding.getAccountName());
        tEnchashmentBinding.setBank(tEnchashmentBinding.getSubbranch());
        ValidatorUtils.validateEntity(tEnchashmentBinding,AddGroup.class);

        //判断商户默认银行卡号
        List<EnchashmentBindingEntity> tEnchashmentBindingEntityList = infoByMerchantId(tEnchashmentBinding.getObjectId());
        if (tEnchashmentBindingEntityList.size() == 0 && tEnchashmentBinding.getIsDefault() == 0){
            return R.error(1,"该商户还没有默认提现账号，请将“是否是默认提现账号”修改为“是”");
        }else if(tEnchashmentBinding.getIsDefault() == 1){
            for (EnchashmentBindingEntity tEnchashmentBindingEntity : tEnchashmentBindingEntityList){
                tEnchashmentBindingEntity.setIsDefault(0);
                tEnchashmentBindingService.updateAllColumnById(tEnchashmentBindingEntity);
            }
        }
        tEnchashmentBindingService.saveEnchashmentBinding(tEnchashmentBinding);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改银行卡")
    @RequestMapping("/update")
    @RequiresPermissions("operation:tenchashmentbinding:update")
    public R update(@RequestBody EnchashmentBindingEntity tEnchashmentBinding){
        ValidatorUtils.validateEntity(tEnchashmentBinding,UpdateGroup.class);

        //判断商户默认银行卡号
        List<EnchashmentBindingEntity> tEnchashmentBindingEntityList = infoByMerchantId(tEnchashmentBinding.getObjectId());
        tEnchashmentBinding.setRealName(tEnchashmentBinding.getAccountName());
        if (tEnchashmentBinding.getIsDefault() == 0 && tEnchashmentBindingService.selectById(tEnchashmentBinding.getId()).getIsDefault() == 1){
            return R.error(1,"该商户还没有默认提现账号，请将“是否是默认提现账号”修改为“是”");
        }else if(tEnchashmentBinding.getIsDefault() == 1){
            for (EnchashmentBindingEntity tEnchashmentBindingEntity : tEnchashmentBindingEntityList){
                tEnchashmentBindingEntity.setIsDefault(0);
                tEnchashmentBindingService.updateAllColumnById(tEnchashmentBindingEntity);
            }
        }
        //变更银行卡状态为修改审核中
        tEnchashmentBinding.setStatus(4);
        tEnchashmentBindingService.updateEnchashmentBinding(tEnchashmentBinding);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除银行卡")
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tenchashmentbinding:delete")
    public R delete(@RequestBody Integer[] ids){
        tEnchashmentBindingService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 禁用
     */
    @SysLog("禁用银行卡")
    @RequestMapping("/stop")
    @RequiresPermissions("operation:tenchashmentbinding:stop")
    public R stop(@RequestBody Integer[] ids){
        tEnchashmentBindingService.stopEnchashmentBinding(ids);

        return R.ok();
    }

    /**
     * 启用
     */
    @SysLog("启用商户")
    @RequestMapping("/start")
    @RequiresPermissions("operation:tenchashmentbinding:start")
    public R start(@RequestBody Integer[] ids){
        tEnchashmentBindingService.startEnchashmentBinding(ids);

        return R.ok();
    }

}
