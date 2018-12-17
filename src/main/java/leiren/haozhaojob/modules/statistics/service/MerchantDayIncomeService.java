package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.MerchantDayIncomeEntity;

import java.util.Map;

/**
 * 商户日收入
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.07.12
 */
public interface MerchantDayIncomeService extends IService<MerchantDayIncomeEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户id获取使用微宝和
     * @param userId
     * @return
     */
    Integer getWBMoney(Integer userId);
}
