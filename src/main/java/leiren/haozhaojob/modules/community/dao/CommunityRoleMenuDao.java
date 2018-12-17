package leiren.haozhaojob.modules.community.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.community.entity.CommunityRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleMenuDao extends BaseMapper<CommunityRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
