package com.scxxwb.net.admin.modules.community.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list = null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs = num.split(",");
            list=Arrays.asList(strs);
        }
        String sysDeptIds = StringUtils.join(list,",");

        Page<CommunityRoleEntity> page = this.selectPage(
                new Query<CommunityRoleEntity>(params).getPage(),
                new EntityWrapper<CommunityRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName),"role_name", roleName)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,"role_id in (select role_id from t_community_sys_role_dept where dept_id in( select dept_id from t_community_sys_dept where sys_dept_id in(" + sysDeptIds  + ")))")
        );

        for(CommunityRoleEntity CommunityRoleEntity : page.getRecords()){
            CommunityUserEntity CommunityUserEntity = communityUserService.selectById(CommunityRoleEntity.getUserId());
            if(CommunityUserEntity != null){
                CommunityRoleEntity.setUserName(CommunityUserEntity.getUserName());
            }
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

    @Override
    @DataFilter(subDept = true, user = false)
    public List<CommunityRoleEntity> selectList(Map<String, Object> params) {
        String sql_filter =(String)params.get(Constant.SQL_FILTER);
        List<String> list = null;
        if (sql_filter != null){
            sql_filter = sql_filter.substring(13,sql_filter.length()-2);
            String [] strs = sql_filter.split(",");
            list=Arrays.asList(strs);
        }
        String sysDeptIds = StringUtils.join(list,",");
        return this.selectList(new EntityWrapper<CommunityRoleEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,"role_id in (select role_id from t_community_sys_role_dept where dept_id in( select dept_id from t_community_sys_dept where sys_dept_id in(" + sysDeptIds  + ")))"));
    }
}
