package com.scxxwb.net.admin.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantIncomeTotalEntity;

import java.util.List;
import java.util.Map;

/**
 * 商户收入统计
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeTotalService extends IService<MerchantIncomeTotalEntity> {

    MerchantIncomeTotalEntity queryPage(Map<String, Object> params);
}
