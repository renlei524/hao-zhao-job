package com.scxxwb.net.admin.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleDao extends BaseMapper<CommunityRoleEntity> {

    public Integer getRole_count(Map map) throws Exception;

    public List<CommunityRoleEntity> getRole(Map map) throws Exception;

}

