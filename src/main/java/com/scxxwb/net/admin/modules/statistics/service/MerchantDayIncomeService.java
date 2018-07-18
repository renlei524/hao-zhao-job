package com.scxxwb.net.admin.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantDayIncomeEntity;

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
}
