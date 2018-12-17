package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.WbCardEntity;

import java.util.Map;

/**
 * 卡券表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-05
 */
public interface WbCardService extends IService<WbCardEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void stopWBCard(Integer [] ids);
}

