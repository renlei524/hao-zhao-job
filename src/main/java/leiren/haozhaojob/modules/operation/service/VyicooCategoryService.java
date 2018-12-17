package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.VyicooCategoryEntity;

import java.util.Map;

/**
 * 经营类目
 *
 */
public interface VyicooCategoryService extends IService<VyicooCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

