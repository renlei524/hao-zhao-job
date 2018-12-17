package leiren.haozhaojob.modules.operation.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;


/**
 * 商户商品自定义分类表
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-17 16:12:45
 */
@RestController
@RequestMapping("operation/merchantproductcustomerlabel")
public class MerchantProductCustomerLabelController {
    @Autowired
    private MerchantProductCustomerLabelService merchantProductCustomerLabelService;

    @Autowired
    private MerchantService merchantService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:merchantproductcustomerlabel:list")
    public R list(@RequestParam Map<String, Object> params){
        String status = "5";
        params.put("status",status);
        PageUtils page = merchantProductCustomerLabelService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list_check")
    @RequiresPermissions("operation:merchantproductcustomerlabel:list")
    public R list_check(@RequestParam Map<String, Object> params){
        String status = "1,3";
        params.put("status",status);
        PageUtils page = merchantProductCustomerLabelService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/select")
    @RequiresPermissions("operation:merchantproductcustomerlabel:select")
    public R select(){
        List<MerchantProductCustomerLabelEntity> list = merchantProductCustomerLabelService.select();
        return R.ok().put("list",list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:merchantproductcustomerlabel:info")
    public R info(@PathVariable("id") Integer id){
        MerchantProductCustomerLabelEntity merchantProductCustomerLabel = merchantProductCustomerLabelService.selectById(id);
        MerchantEntity merchantEntity = merchantService.selectById(merchantProductCustomerLabel.getMerchantId());
        if (merchantEntity != null){
            merchantProductCustomerLabel.setMerchantName(merchantEntity.getMerchantName());
        }
        if (merchantProductCustomerLabel.getMerchantId().equals(0)){
            merchantProductCustomerLabel.setMerchantName("商家通用标签");
        }
        return R.ok().put("merchantProductCustomerLabel", merchantProductCustomerLabel);
    }

    /**
     * 审核
     */
    @SysLog("审核商品标签")
    @RequestMapping("/check")
    @RequiresPermissions("operation:merchantproductcustomerlabel:check")
    public R check(@RequestBody MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity){
        ValidatorUtils.validateEntity(merchantProductCustomerLabelEntity);
        merchantProductCustomerLabelService.updateAllColumnById(merchantProductCustomerLabelEntity);
        return R.ok();
    }

    /**
     * 保存
     */
    @SysLog("保存通用商品标签")
    @RequestMapping("/save")
    @RequiresPermissions("operation:merchantproductcustomerlabel:save")
    public R save(@RequestBody MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity){
        merchantProductCustomerLabelEntity.setCreateTime(new Date());
        merchantProductCustomerLabelEntity.setStatus(5);
        merchantProductCustomerLabelEntity.setMerchantId(0);
        merchantProductCustomerLabelEntity.setSortNo(1);
        merchantProductCustomerLabelEntity.setIsDelete(0);
        ValidatorUtils.validateEntity(merchantProductCustomerLabelEntity,AddGroup.class);
        merchantProductCustomerLabelService.insert(merchantProductCustomerLabelEntity);
        return R.ok();
    }
}
