package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

