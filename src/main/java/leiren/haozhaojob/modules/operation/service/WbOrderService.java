package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.WbOrderEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单数据表0
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
public interface WbOrderService extends IService<WbOrderEntity> {

    PageUtils selectByUserName(Map<String, Object> params);

    PageUtils selectByMerchantId(Map<String, Object> params);

    WbOrderEntity selectTWBUserTotalAndArea(Integer userId);

    Map<String, Date> selectTWBUserRecconsumptionTime(Integer userId);

    Double totalIncomeByMerchantId(Integer merchantId);

    Map<String, Object> selectCashAmountByMerchantId(Date startTime, Date endTime, Integer merchantId);

    Integer selectWeiBaoAmountByMerchantId(Date startTime, Date endTime, Integer merchantId);

    int selectOrderDetailsByMerchantId(Map<String, Object> params);

    List<WbOrderEntity> selectWBOrderByMerchantIdAndDate(Map<String, Object> params);

    PageUtils selectWBOrder(Map<String, Object> params);

    Long selectOrderCountsByMerchantId(Map<String, Object> params);
}

