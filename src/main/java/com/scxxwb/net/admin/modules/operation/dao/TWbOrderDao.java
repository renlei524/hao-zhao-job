package com.scxxwb.net.admin.modules.operation.dao;

import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
public interface TWbOrderDao extends BaseMapper<TWbOrderEntity> {
    /**
     * 根据商户名模糊查询
     * @return
     */
    public List<TWbOrderEntity> selectByUserName(Map<String, Object> params);

    /**
     * 查询记录数
     * @return
     */
    public int total(Map<String, Object> params);

    /**
     * 根据商户查询日交易
     * @return
     */
    public List<TWbOrderEntity> selectByMerchantId(Map<String, Object> params);

    /**
     * 查询日交易记录数
     * @return
     */
    public int totalByMerchantId(Map<String, Object> params);

    /**
     * 用户管理展示，查询消费总额和最近消费区域id
     * @param userId 用户id
     * @return
     */
    TWbOrderEntity selectTWBUserTotalAndArea(Integer userId);

    /**
     * 用户管理展示，查询最近消费时间
     * @param userId 用户id
     * @return
     */
    Map<String, Date> selectTWBUserRecconsumptionTime(Integer userId);

    /**
     * 根据商户id查询总收入
     * @param merchantId
     * @return
     */
    Double totalIncomeByMerchantId(Integer merchantId);
}
