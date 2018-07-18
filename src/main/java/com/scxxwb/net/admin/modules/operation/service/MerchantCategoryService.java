package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-11 17:06:19
 */
public interface MerchantCategoryService extends IService<MerchantCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MerchantCategoryEntity> queryList(Map<String, Object> map);

    /**
     * 查询子店铺分类
     * @param parentId
     * @return
     */
    List<Long> queryMerchantCategoryIdList(Long parentId);
}

