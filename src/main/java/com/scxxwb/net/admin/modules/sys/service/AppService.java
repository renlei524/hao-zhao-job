package com.scxxwb.net.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.sys.entity.AppEntity;

import java.util.Map;

public interface AppService extends IService<AppEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
