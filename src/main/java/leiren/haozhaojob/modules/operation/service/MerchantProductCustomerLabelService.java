package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;

import java.util.List;
import java.util.Map;

/**
 * 商户商品自定义分类表
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-16 15:03:54
 */
public interface MerchantProductCustomerLabelService extends IService<MerchantProductCustomerLabelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MerchantProductCustomerLabelEntity> select();
}

