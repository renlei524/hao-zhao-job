package leiren.haozhaojob.modules.operation.dao;

import leiren.haozhaojob.modules.operation.entity.VyicooJinjianEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * 
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
public interface VyicooJinjianDao extends BaseMapper<VyicooJinjianEntity> {
    void updatestatus(Map map);
}
