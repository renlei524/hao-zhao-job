package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TWbCardEntity;

import java.util.Map;

/**
 * 卡券表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
public interface TWbCardService extends IService<TWbCardEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

