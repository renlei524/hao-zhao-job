package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.MerchantCheckEntity;

import java.util.Map;

/**
 * 商户审核信息表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
public interface MerchantCheckService extends IService<MerchantCheckEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deletByMerchantIds(Integer[] merchantIds);

    MerchantCheckEntity selectByMerchantId(Integer merchantId);
}

