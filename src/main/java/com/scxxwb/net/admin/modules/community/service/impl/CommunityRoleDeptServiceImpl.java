package com.scxxwb.net.admin.modules.community.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.modules.community.dao.CommunityRoleDeptDao;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleDeptEntity;
import com.scxxwb.net.admin.modules.community.service.CommunityRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色与部门对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("CommunityRoleDeptService")
public class CommunityRoleDeptServiceImpl extends ServiceImpl<CommunityRoleDeptDao, CommunityRoleDeptEntity> implements CommunityRoleDeptService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与部门关系
        deleteBatch(new Long[]{roleId});

        if(deptIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<CommunityRoleDeptEntity> list = new ArrayList<>(deptIdList.size());
        for(Long deptId : deptIdList){
            CommunityRoleDeptEntity CommunityRoleDeptEntity = new CommunityRoleDeptEntity();
            CommunityRoleDeptEntity.setDeptId(deptId);
            CommunityRoleDeptEntity.setRoleId(roleId);

            list.add(CommunityRoleDeptEntity);
        }
        this.insertBatch(list);
    }

    @Override
    public List<Long> queryDeptIdList(Long[] roleIds) {
        return baseMapper.queryDeptIdList(roleIds);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
