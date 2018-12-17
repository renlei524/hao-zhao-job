package leiren.haozhaojob.modules.operation.controller;

import java.util.Map;

import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingCheckEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.EnchashmentBindingCheckService;
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


/**
 * 商户与用户的提现账户绑定
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:39:06
 */
@RestController
@RequestMapping("operation/enchashmentBindingCheck")
public class EnchashmentBindingCheckController extends AbstractController {
    @Autowired
    private EnchashmentBindingCheckService enchashmentBindingCheckService;
    @Autowired
    private MerchantService merchantService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:enchashmentBindingCheck:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = enchashmentBindingCheckService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:enchashmentBindingCheck:info")
    public R info(@PathVariable("id") Integer id){
        EnchashmentBindingCheckEntity enchashmentBindingCheck = enchashmentBindingCheckService.selectById(id);
        //获取商户id
        Integer merchantId = enchashmentBindingCheck.getObjectId();
        MerchantEntity merchantEntity = merchantService.selectById(merchantId);
        enchashmentBindingCheck.setObjectName(merchantEntity.getMerchantName());
        return R.ok().put("enchashmentBindingCheck", enchashmentBindingCheck);
    }

    /**
     * 审核
     */
    @RequestMapping("/check")
    @RequiresPermissions("operation:enchashmentBindingCheck:check")
    public R check(@RequestBody EnchashmentBindingCheckEntity enchashmentBindingCheck){
        ValidatorUtils.validateEntity(enchashmentBindingCheck);
        enchashmentBindingCheckService.checkEnchashmentBindingCheck(enchashmentBindingCheck);//全部更新
        
        return R.ok();
    }

}
