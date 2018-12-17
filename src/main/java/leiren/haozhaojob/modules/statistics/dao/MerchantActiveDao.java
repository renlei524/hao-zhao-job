package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.MerchantActiveEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活跃商户
 *
 */
@Service
public interface MerchantActiveDao extends BaseMapper<MerchantActiveEntity> {
        List<MerchantActiveEntity> selectMerchantByMerchantNameAndDate(Map<String, Object> params);
}
