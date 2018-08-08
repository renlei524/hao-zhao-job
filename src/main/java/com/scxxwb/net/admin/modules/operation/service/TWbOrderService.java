package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;

import java.util.Map;

/**
 * 订单数据表0
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
public interface TWbOrderService extends IService<TWbOrderEntity> {

    PageUtils selectByUserName(Map<String, Object> params);

    PageUtils selectByMerchantId(Map<String, Object> params);

    Double totalIncomeByMerchantId(Integer merchantId);
}

