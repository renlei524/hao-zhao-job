package com.scxxwb.net.admin.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.operation.entity.TWbUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户基础信息表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
public interface TWbUserService extends IService<TWbUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TWbUserEntity> queryList(Map<String, Object> map);

    Boolean saveUser(TWbUserEntity tWbUserEntity);
}

