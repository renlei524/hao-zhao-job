package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TVyicooAreaEntity;

import java.util.Map;

/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 14:20:08
 */
public interface TVyicooAreaService extends IService<TVyicooAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

