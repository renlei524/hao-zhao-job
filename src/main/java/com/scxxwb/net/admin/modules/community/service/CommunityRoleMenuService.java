package com.scxxwb.net.admin.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleMenuService extends IService<CommunityRoleMenuEntity> {

    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

}


