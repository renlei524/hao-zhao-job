package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.WbOrderDetailsEntity;

import java.util.Map;

/**
 * 订单明细数据表0
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-29 14:54:25
 */
public interface WbOrderDetailsService extends IService<WbOrderDetailsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

