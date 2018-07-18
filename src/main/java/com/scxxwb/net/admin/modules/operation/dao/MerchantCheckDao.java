package com.scxxwb.net.admin.modules.operation.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCheckEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 商户审核信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
public interface MerchantCheckDao extends BaseMapper<MerchantCheckEntity> {
    void deleteByMerchantIds(Integer[] merchantIds);

    MerchantCheckEntity selectByMerchantId(Map<String, Object> map);
}
