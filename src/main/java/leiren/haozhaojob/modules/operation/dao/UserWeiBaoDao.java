package leiren.haozhaojob.modules.operation.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.operation.entity.UserWeiBaoEntity;

import java.util.List;
import java.util.Map;

public interface UserWeiBaoDao extends BaseMapper<UserWeiBaoEntity> {

    /**
     * 根据分页获取微宝用户信息
     * @param params
     * @return
     */
    List<UserWeiBaoEntity> selectUserWeiBao(Map<String, Object> params);

    /**
     * 获取总记录数
     * @param params
     * @return
     */
    int totalUserWeiBao(Map<String, Object> params);

}
