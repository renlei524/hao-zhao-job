package com.scxxwb.net.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Integer> queryDetpIdList(Integer parentId);

    /**
     * 根据id修改信息
     * @param arr
     * @return
     */
    int updateDeptByParentId(List<Integer> arr);

}
