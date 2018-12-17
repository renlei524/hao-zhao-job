package leiren.haozhaojob.modules.community.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.modules.community.dao.CommunityRoleMenuDao;
import leiren.haozhaojob.modules.community.entity.CommunityRoleMenuEntity;
import leiren.haozhaojob.modules.community.service.CommunityRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("CommunityRoleMenuService")
public class CommunityRoleMenuServiceImpl extends ServiceImpl<CommunityRoleMenuDao, CommunityRoleMenuEntity> implements CommunityRoleMenuService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<CommunityRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
        for(Long menuId : menuIdList){
            CommunityRoleMenuEntity CommunityRoleMenuEntity = new CommunityRoleMenuEntity();
            CommunityRoleMenuEntity.setMenuId(menuId);
            CommunityRoleMenuEntity.setRoleId(roleId);

            list.add(CommunityRoleMenuEntity);
        }
        this.insertBatch(list);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
