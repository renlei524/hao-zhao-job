package com.scxxwb.net.admin.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.community.entity.CommunitySysDeptEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 社区查询
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Repository
public interface CommunitySysDeptDao extends BaseMapper<CommunitySysDeptEntity> {
    //社区账号管理按照社区名称模糊查询社区id
    List<String> selectDeptIdByName(String name);
}
