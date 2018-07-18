package com.scxxwb.net.admin.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysRoleDeptEntity;

import java.util.List;


/**
 * 角色与部门对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleDeptDao extends BaseMapper<CommunityRoleDeptEntity> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long[] roleIds);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
