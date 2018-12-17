package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.EnchashmentBindingEntity;

import java.util.Map;

/**
 * 商户与用户的提现账户绑定
 *
 * @author liyun
 * @email 2563720820@qq.com
 * @date 2018-07-10 10:10:11
 */
public interface EnchashmentBindingService extends IService<EnchashmentBindingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveEnchashmentBinding(EnchashmentBindingEntity enchashmentBindingEntity);

    void updateEnchashmentBinding(EnchashmentBindingEntity enchashmentBindingEntity);

    void stopEnchashmentBinding(Integer[] ids);

    void startEnchashmentBinding(Integer[] ids);
}

