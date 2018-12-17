package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.modules.operation.entity.VyicooAreaEntity;

import java.util.List;

/**
 * 
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 14:20:08
 */
public interface VyicooAreaService extends IService<VyicooAreaEntity> {

    List<VyicooAreaEntity> queryPage(Integer areaCode);
}

