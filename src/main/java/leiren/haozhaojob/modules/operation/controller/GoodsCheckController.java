package leiren.haozhaojob.modules.operation.controller;

import java.math.BigDecimal;
import java.util.Map;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.modules.operation.entity.GoodsCheckEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;
import leiren.haozhaojob.modules.operation.service.GoodsCheckService;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品信息审核
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
@RestController
@RequestMapping("operation/goodscheck")
public class GoodsCheckController extends AbstractController {
    @Value(value = "${scxxwb.nginx.goodsPath}")
    private String goodsNginxPath;

    @Autowired
    private GoodsCheckService goodsCheckService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantProductCustomerLabelService merchantProductCustomerLabelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:goodsCheck:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsCheckService.queryPage(params);
        return R.ok().put("page", page).put("imageNginxPath", goodsNginxPath);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:goodsCheck:info")
    public R info(@PathVariable("id") Integer id){
        GoodsCheckEntity goodsCheck = goodsCheckService.selectById(id);
        goodsCheck.setOriginalPrice(goodsCheck.getOriginalPrice().divide(new BigDecimal("100")));
        goodsCheck.setMarketPrice(goodsCheck.getMarketPrice().divide(new BigDecimal("100")));
        goodsCheck.setDiscountPrice(goodsCheck.getDiscountPrice().divide(new BigDecimal("100")));
        goodsCheck.setPromotionalPrice(goodsCheck.getPromotionalPrice().divide(new BigDecimal("100")));
        MerchantEntity merchantEntity = merchantService.selectById(goodsCheck.getMerchantId());
        if (merchantEntity != null){
            goodsCheck.setMerchantName(merchantEntity.getMerchantName());
        }
        MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity = merchantProductCustomerLabelService.selectById(goodsCheck.getLabelId());
        if (merchantProductCustomerLabelEntity != null){
            goodsCheck.setLabelName(merchantProductCustomerLabelEntity.getLabelName());
        }
        return R.ok().put("goodsCheck", goodsCheck).put("imageNginxPath", goodsNginxPath);
    }

    /**
     * 审核
     */
    @SysLog("审核商品")
    @RequestMapping("/check")
    @RequiresPermissions("operation:goodsCheck:check")
    public R check(@RequestBody GoodsCheckEntity tGoodsCheck){
        ValidatorUtils.validateEntity(tGoodsCheck);
        goodsCheckService.goodsCheck(tGoodsCheck);
        return R.ok();
    }

    @SysLog("批量商品审核通过")
    @RequestMapping("/batchOperationOk/{ids}")
    @RequiresPermissions("operation:goodsCheck:check")
    public R batchOperationOk(@RequestParam Map<String, Object> params, @PathVariable("ids") String ids){
        params.put("ids",ids);
        params.put("status",5);
        goodsCheckService.batchOperation(params);
        return R.ok();
    }

    @SysLog("批量商品审核不通过")
    @RequestMapping("/batchOperationNo/{ids}/{remark}")
    @RequiresPermissions("operation:goodsCheck:check")
    public R batchOperationNo(@RequestParam Map<String, Object> params, @PathVariable("ids") String ids, @PathVariable("remark") String remark){
        params.put("ids",ids);
        params.put("remark",remark);
        params.put("status",2);
        goodsCheckService.batchOperation(params);
        return R.ok();
    }

}
