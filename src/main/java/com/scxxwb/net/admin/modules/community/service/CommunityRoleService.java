package com.scxxwb.net.admin.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleEntity;

import java.util.Map;
/**
 * 角色
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleService extends IService<CommunityRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(CommunityRoleEntity role);

    void update(CommunityRoleEntity role);

    void deleteBatch(Long[] roleIds);
}
