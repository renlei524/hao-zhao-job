package com.scxxwb.net.admin.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.modules.community.entity.CommunitySysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 社区管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunitySysDeptService extends IService<CommunitySysDeptEntity> {
    /**
     * 按条件分页查询社区对象
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    List<CommunitySysDeptEntity> queryList(Map<String, Object> map);

    List<String> selectDeptIdByName(String name);
}
