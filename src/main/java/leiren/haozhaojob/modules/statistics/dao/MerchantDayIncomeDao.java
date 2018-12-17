package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.MerchantDayIncomeEntity;

import java.util.List;
import java.util.Map;

/**
 * 商户日收入
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.07.12
 */
public interface MerchantDayIncomeDao extends BaseMapper<MerchantDayIncomeEntity> {

    /**
     * 获取商户信息
     * @param params
     * @return
     */
    List<MerchantDayIncomeEntity> GetMerchant(Map<String, Object> params);

    /**
     * 获取总记录数
     * @param params
     * @return
     */
    int total(Map<String, Object> params);

    /**
     * 获取日交易数据
     * @param params
     * @return
     */
    MerchantDayIncomeEntity GetTransactionAmount(Map<String, Object> params);

    /**
     * 获取原始通道总手续费
     * @param params
     * @return
     */
    String getYuanShi(Map<String, Object> params);

    /**
     * 获取其他通道总手续费
     * @param params
     * @return
     */
    String getQiTa(Map<String, Object> params);

    /**
     * 根据用户id获取使用微宝和
     * @param userId
     * @return
     */
    Integer getWBMoney(Integer userId);
}
