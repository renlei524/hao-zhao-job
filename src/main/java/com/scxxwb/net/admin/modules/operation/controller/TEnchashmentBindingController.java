package com.scxxwb.net.admin.modules.operation.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import com.scxxwb.net.admin.modules.sys.entity.SysDictEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.TEnchashmentBindingEntity;
import com.scxxwb.net.admin.modules.operation.service.TEnchashmentBindingService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 商户与用户的提现账户绑定
 *
 * @author liyun
 * @email 2563720820@qq.com
 * @date 2018-07-10 10:10:11
 */
@RestController
@RequestMapping("operation/tenchashmentbinding")
public class TEnchashmentBindingController {
    @Autowired
    private TEnchashmentBindingService tEnchashmentBindingService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    SysDictService sysDictService;

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
        TEnchashmentBindingEntity tEnchashmentBinding = tEnchashmentBindingService.selectById(id);
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
    private List<TEnchashmentBindingEntity> infoByMerchantId(Integer merchantId){
        Map map = new HashMap<String, Object>();
        map.put("object_id",merchantId);
        List<TEnchashmentBindingEntity> list = tEnchashmentBindingService.selectByMap(map);
        return list;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:tenchashmentbinding:save")
    public R save(@RequestBody TEnchashmentBindingEntity tEnchashmentBinding){
        tEnchashmentBinding.setStatus(1);
        tEnchashmentBinding.setRealName(tEnchashmentBinding.getAccountName());
        tEnchashmentBinding.setBank(tEnchashmentBinding.getSubbranch());
        ValidatorUtils.validateEntity(tEnchashmentBinding,AddGroup.class);

        //判断商户默认银行卡号
        List<TEnchashmentBindingEntity> tEnchashmentBindingEntityList = infoByMerchantId(tEnchashmentBinding.getObjectId());
        if (tEnchashmentBindingEntityList.size() == 0 && tEnchashmentBinding.getIsDefault() == 0){
            return R.error(1,"该商户还没有默认提现账号，请将“是否是默认提现账号”修改为“是”");
        }else if(tEnchashmentBinding.getIsDefault() == 1){
            for (TEnchashmentBindingEntity tEnchashmentBindingEntity : tEnchashmentBindingEntityList){
                tEnchashmentBindingEntity.setIsDefault(0);
                tEnchashmentBindingService.updateAllColumnById(tEnchashmentBindingEntity);
            }
        }
        tEnchashmentBindingService.insert(tEnchashmentBinding);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:tenchashmentbinding:update")
    public R update(@RequestBody TEnchashmentBindingEntity tEnchashmentBinding){
        ValidatorUtils.validateEntity(tEnchashmentBinding,UpdateGroup.class);

        //判断商户默认银行卡号
        List<TEnchashmentBindingEntity> tEnchashmentBindingEntityList = infoByMerchantId(tEnchashmentBinding.getObjectId());
        tEnchashmentBinding.setRealName(tEnchashmentBinding.getAccountName());
        if (tEnchashmentBinding.getIsDefault() == 0 && tEnchashmentBindingService.selectById(tEnchashmentBinding.getId()).getIsDefault() == 1){
            return R.error(1,"该商户还没有默认提现账号，请将“是否是默认提现账号”修改为“是”");
        }else if(tEnchashmentBinding.getIsDefault() == 1){
            for (TEnchashmentBindingEntity tEnchashmentBindingEntity : tEnchashmentBindingEntityList){
                tEnchashmentBindingEntity.setIsDefault(0);
                tEnchashmentBindingService.updateAllColumnById(tEnchashmentBindingEntity);
            }
        }

        tEnchashmentBindingService.updateAllColumnById(tEnchashmentBinding);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tenchashmentbinding:delete")
    public R delete(@RequestBody Integer[] ids){
        tEnchashmentBindingService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
