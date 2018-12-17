package leiren.haozhaojob.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.modules.community.entity.CommunityRoleDeptEntity;

import java.util.List;



    /**
     * 角色与部门对应关系
     *
     * @author leiren
     * @email renlei@scxxwb.com
     * @date 2018.05.28
     */
    public interface CommunityRoleDeptService extends IService<CommunityRoleDeptEntity> {

        void saveOrUpdate(Long roleId, List<Long> deptIdList);

        /**
         * 根据角色ID，获取部门ID列表
         */
        List<Long> queryDeptIdList(Long[] roleIds) ;

        /**
         * 根据角色ID数组，批量删除
         */
        int deleteBatch(Long[] roleIds);
    }


