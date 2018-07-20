package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TVyicooJinjianEntity;

import java.util.Map;

/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
public interface TVyicooJinjianService extends IService<TVyicooJinjianEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

