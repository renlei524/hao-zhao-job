package com.scxxwb.net.admin.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantIncomeDetailEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeDetailService extends IService<MerchantIncomeDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
