package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.VyicooJinjianEntity;

import java.util.Map;

/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
public interface VyicooJinjianService extends IService<VyicooJinjianEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatus(Map map);
}

