package com.scxxwb.net.admin.modules.operation.dao;

import com.scxxwb.net.admin.modules.operation.entity.MerchantCategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-11 17:06:19
 */
public interface MerchantCategoryDao extends BaseMapper<MerchantCategoryEntity> {
    /***
     * 查询子店铺分类
     * @param parentId
     * @return
     */
    List<Long> queryMerchantCategoryIdList(Long parentId);
}
