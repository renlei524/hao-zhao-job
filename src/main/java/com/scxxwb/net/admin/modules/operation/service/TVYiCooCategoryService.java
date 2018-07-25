package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.GoodsEntity;
import com.scxxwb.net.admin.modules.operation.entity.TVYiCooCategoryEntity;

import java.util.Map;

/**
 * 经营类目
 *
 */
public interface TVYiCooCategoryService extends IService<TVYiCooCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

