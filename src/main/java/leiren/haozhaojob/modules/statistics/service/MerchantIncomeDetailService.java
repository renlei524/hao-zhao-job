package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeDetailEntity;

import java.util.Map;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeDetailService extends IService<MerchantIncomeDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
