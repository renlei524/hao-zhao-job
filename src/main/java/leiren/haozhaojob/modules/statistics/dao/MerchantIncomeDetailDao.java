package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeDetailEntity;

import java.util.Map;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeDetailDao extends BaseMapper<MerchantIncomeDetailEntity> {

    MerchantIncomeDetailEntity getMerchantIncomeDetailBymerchantId(Map<String, Object> params);
}
