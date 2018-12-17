package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.MerchantActiveEntity;

import java.util.List;
import java.util.Map;

/**
 * 活跃商户
 */
public interface MerchantActiveService extends IService<MerchantActiveEntity> {

    PageUtils queryPage(Map<String, Object> params);


    Page<MerchantActiveEntity> selectMerchantByMerchantName(Map<String, Object> params, String name);

    List<MerchantActiveEntity> selectMerchantByMerchantNameAndDate(Map<String, Object> params);

    int selectMerchantCountByName(String name);
}
