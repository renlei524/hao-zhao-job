package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.GoodsSkuEntity;

import java.util.Map;

/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
public interface GoodsSkuService extends IService<GoodsSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

