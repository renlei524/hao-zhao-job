package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.JinJianEntity;

import java.util.Map;

public interface JinJianService extends IService<JinJianEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
