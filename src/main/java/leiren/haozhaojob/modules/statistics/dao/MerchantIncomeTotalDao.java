package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeTotalEntity;

import java.util.Map;

/**
 * 商户收入统计
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeTotalDao extends BaseMapper<MerchantIncomeTotalEntity> {

   MerchantIncomeTotalEntity getMerchantIncomeTotalByArea(Map<String, Object> params);

   MerchantIncomeTotalEntity getMerchantIncomeTotalByAlipay(Map<String, Object> params);

}
