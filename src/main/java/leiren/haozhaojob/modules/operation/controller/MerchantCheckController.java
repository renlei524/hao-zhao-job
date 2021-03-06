package leiren.haozhaojob.modules.operation.controller;

import com.alibaba.fastjson.JSONObject;
import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.modules.operation.entity.MerchantCheckEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantCheckService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.modules.sys.entity.SysDeptEntity;
import leiren.haozhaojob.modules.sys.service.SysDeptService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 商户审核信
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
@RestController
@RequestMapping("operation/check")
public class MerchantCheckController extends AbstractController {
    @Value(value = "${scxxwb.nginx.merchantPath}")
    private String merchantNginxPath;

    @Autowired
    private MerchantCheckService merchantCheckService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SysDeptService sysDeptService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:check:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = merchantCheckService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:check:info")
    public R info(@PathVariable("id") Integer id){
        MerchantCheckEntity merchantCheckEntity = merchantCheckService.selectById(id);
        merchantCheckEntity.setMerchantLimit(merchantCheckEntity.getMerchantLimit() / 100);
        SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchantCheckEntity.getAgentId());
        merchantCheckEntity.setAgentName(sysDeptEntity.getName());
        return R.ok().put("merchant", merchantCheckEntity).put("imageNginxPath", merchantNginxPath);
    }


    /**
     * 审核
     */
    @SysLog("审核商户")
    @RequestMapping("/check")
    @RequiresPermissions("operation:check:check")
    public R check(@RequestBody MerchantCheckEntity merchantCheckEntity){
        ValidatorUtils.validateEntity(merchantCheckEntity);
        merchantCheckEntity.setUpdateTime(new Date());
        merchantCheckEntity.setMerchantLimit(merchantCheckEntity.getMerchantLimit() * 100);
        if(merchantCheckEntity.getStatus() == 5) {
            Map map = new HashMap<String, Object>();
            map.put("merchantCode", merchantCheckEntity.getMerchantCode());
            map.put("merchantName", merchantCheckEntity.getMerchantName());
            MerchantEntity merchantName = merchantService.getMerchantByMerchantName(map);
            if(merchantName != null && !merchantName.getId().equals(merchantCheckEntity.getMerchantId()) && merchantName.getMerchantName().equals(merchantCheckEntity.getMerchantName())) {
                return R.error("当前商户名称（店铺名称）已存在！");
            }
            MerchantEntity merchantCode = merchantService.getMerchantByCode(map);
            if( merchantCode != null && !merchantCode.getId().equals(merchantCheckEntity.getMerchantId()) && merchantCode.getMerchantCode().equals(merchantCheckEntity.getMerchantCode())) {
                return R.error("当前收款码已存在！");
            }
            String merchantCheckJson = JSONObject.toJSONString(merchantCheckEntity);
            MerchantEntity merchantEntity = JSONObject.toJavaObject(JSONObject.parseObject(merchantCheckJson), MerchantEntity.class);
            merchantEntity.setId(merchantCheckEntity.getMerchantId());
            merchantService.updateAllColumnById(merchantEntity);
        } else {
            MerchantEntity merchantEntity = merchantService.selectById(merchantCheckEntity.getMerchantId());
            // 修改前状态是否为：正常或禁用
            if(merchantEntity.getStatus() != 5 && merchantEntity.getStatus() != 6) {
                merchantService.updateStatusAndRemarkAndUpdateTime(merchantCheckEntity.getMerchantId(), merchantCheckEntity.getStatus(), merchantCheckEntity.getRemark(), merchantCheckEntity.getUpdateTime());
            }
        }
        merchantCheckService.updateAllColumnById(merchantCheckEntity);//全部更新

        return R.ok();
    }
}
