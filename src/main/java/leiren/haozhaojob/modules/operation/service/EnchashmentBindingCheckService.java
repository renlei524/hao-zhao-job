package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingCheckEntity;

import java.util.Map;

/**
 * 商户与用户的提现账户绑定
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:39:06
 */
public interface EnchashmentBindingCheckService extends IService<EnchashmentBindingCheckEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void checkEnchashmentBindingCheck(EnchashmentBindingCheckEntity enchashmentBindingCheckEntity);

    EnchashmentBindingCheckEntity selectEnchashmentBindingCheckByEnchashmentIdAndStatus(EnchashmentBindingCheckEntity enchashmentBindingCheckEntity);
}

