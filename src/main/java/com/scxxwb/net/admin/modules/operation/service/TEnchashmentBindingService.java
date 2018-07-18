package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TEnchashmentBindingEntity;

import java.util.Map;

/**
 * 商户与用户的提现账户绑定
 *
 * @author liyun
 * @email 2563720820@qq.com
 * @date 2018-07-10 10:10:11
 */
public interface TEnchashmentBindingService extends IService<TEnchashmentBindingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

