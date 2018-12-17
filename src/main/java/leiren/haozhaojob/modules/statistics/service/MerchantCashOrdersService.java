package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.MerchantCashOrdersEntity;

import java.util.Map;

/**
 * 商家提现打包记录订单表
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-14 09:27:44
 */
public interface MerchantCashOrdersService extends IService<MerchantCashOrdersEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

