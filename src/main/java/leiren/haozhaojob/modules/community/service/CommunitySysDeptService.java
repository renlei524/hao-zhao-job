package leiren.haozhaojob.modules.community.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.community.entity.CommunitySysDeptEntity;

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
