package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.GoodsCheckEntity;

import java.util.Map;

/**
 * 商品信息审核
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
public interface GoodsCheckService extends IService<GoodsCheckEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void goodsCheck(GoodsCheckEntity goodsCheck);

    void batchOperation(Map<String, Object> params);
}

