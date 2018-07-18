package com.scxxwb.net.admin.modules.community.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.community.dao.CommunityRoleDao;
import com.scxxwb.net.admin.modules.community.entity.CommunityRoleEntity;
import com.scxxwb.net.admin.modules.community.entity.CommunityUserEntity;
import com.scxxwb.net.admin.modules.community.service.*;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("CommunityRoleService")
public class CommunityRoleServiceImpl extends ServiceImpl<CommunityRoleDao, CommunityRoleEntity> implements CommunityRoleService {
    @Autowired
    private CommunityRoleMenuService communityRoleMenuService;
    @Autowired
    private CommunityRoleDeptService communityRoleDeptService;
    @Autowired
    private CommunityUserRoleService communityUserRoleService;
    @Autowired
    private CommunityUserService communityUserService;
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    /*@DataFilter(subDept = true, user = false)*/
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询条件
        String roleName = (String)params.get("roleName");
        //初始化对象
        Page<CommunityRoleEntity> page=new Page<CommunityRoleEntity>();
        try {
            //获取数据
            Map<String, Object> pMap = new HashMap<String, Object>();
            //管理员开放所有数据权限(参照目前已有结构直接写死的方式类区分管理员身份)
            if(ShiroUtils.getUserEntity().getUserId() != Constant.SUPER_ADMIN) pMap.put("user_id", ShiroUtils.getUserEntity().getUserId());
            pMap.put("start", ((new Query<CommunityRoleEntity>(params).getPage().getCurrent()-1)*new Query<CommunityRoleEntity>(params).getPage().getSize()));
            pMap.put("row", new Query<CommunityRoleEntity>(params).getPage().getSize());
            pMap.put("role_name", roleName);
            page.setRecords(baseMapper.getRole(pMap));
            //获取每页数据数量
            page.setSize(new Query<CommunityRoleEntity>(params).getPage().getSize());
            //获取页码
            page.setCurrent(new Query<CommunityRoleEntity>(params).getPage().getCurrent());
            //获取总页数
            page.setTotal(baseMapper.getRole_count(pMap));

            for(CommunityRoleEntity CommunityRoleEntity : page.getRecords()){
                CommunityUserEntity CommunityUserEntity = communityUserService.selectById(CommunityRoleEntity.getUserId());
                if(CommunityUserEntity != null){
                    CommunityRoleEntity.setUserName(CommunityUserEntity.getUserName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CommunityRoleEntity role) {
        role.setUserId(Long.valueOf(ShiroUtils.getUserEntity().getUserId()));
        role.setCreateTime(new Date());
        this.insert(role);

        //保存角色与菜单关系
        communityRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        communityRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CommunityRoleEntity role) {
        this.updateAllColumnById(role);

        //更新角色与菜单关系
        communityRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        communityRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        communityRoleMenuService.deleteBatch(roleIds);

        //删除角色与部门关联
        communityRoleDeptService.deleteBatch(roleIds);

        //删除角色与用户关联
        communityUserRoleService.deleteBatch(roleIds);
    }
}
