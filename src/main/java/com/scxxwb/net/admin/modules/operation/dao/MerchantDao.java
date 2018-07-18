package com.scxxwb.net.admin.modules.operation.dao;

import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 商户信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
public interface MerchantDao extends BaseMapper<MerchantEntity> {

    List<Integer> getMerchant(List<String> ages);

    Integer getMerchantByLoginUserName(Map<String, Object> params);

    MerchantEntity getMerchantByCode(Map<String, Object> params);

    MerchantEntity getMerchantByMerchantName(Map<String, Object> params);

    int getMerchantByAreaTotal (Map<String, Object> params);

    List<MerchantEntity> getMerchantByArea(Map<String, Object> params);

    Boolean updateStatus(Map<String, Object> map);

    Boolean updateStatusAndRemark(Map<String, Object> map);
}
