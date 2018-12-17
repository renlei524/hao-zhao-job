package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.WbOrderFreeEntity;

import java.util.Map;

/**
 * 免单管理
 *
 */
public interface WbOrderFreeService extends IService<WbOrderFreeEntity> {

    PageUtils selectTWBOrderFreeBySearchCondition(Map<String, Object> params);
}
