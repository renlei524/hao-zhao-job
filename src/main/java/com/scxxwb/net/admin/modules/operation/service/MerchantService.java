package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;

import java.util.List;
import java.util.Map;

/**
 * 商户信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
public interface MerchantService extends IService<MerchantEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Integer> getMerchant(List<String> ages);

    Integer getMerchantByLoginUserName(Map<String, Object> params);

    MerchantEntity getMerchantByCode(Map<String, Object> params);

    MerchantEntity getMerchantByMerchantName(Map<String, Object> params);

    int getMerchantByAreaTotal (Map<String, Object> params);

    List<MerchantEntity> getMerchantByArea(Map<String, Object> params);

    Boolean updateStatus(Integer id, Integer status);

    Boolean updateStatusAndRemark(Integer id, Integer status, String remark);

    void updateMerchant(MerchantEntity merchant);

    void saveMerchant(MerchantEntity merchant, Map map);

    void stopMerchant(Integer[] ids);

    void startMerchant(Integer[] ids);
}

