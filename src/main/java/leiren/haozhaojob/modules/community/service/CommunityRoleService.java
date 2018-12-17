package leiren.haozhaojob.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.community.entity.CommunityRoleEntity;

import java.util.List;
import java.util.Map;
/**
 * 角色
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface CommunityRoleService extends IService<CommunityRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(CommunityRoleEntity role);

    void update(CommunityRoleEntity role);

    void deleteBatch(Long[] roleIds);

    List<CommunityRoleEntity> selectList(Map<String, Object> params);
}
