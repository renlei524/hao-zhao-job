package leiren.haozhaojob.modules.operation.dao;

import leiren.haozhaojob.modules.operation.entity.GoodsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
public interface GoodsDao extends BaseMapper<GoodsEntity> {

    /**
     * 根据商户id获取商品数量
     * @param merchantId
     * @return
     */
    Integer totalByMerchantId(Integer merchantId);

    /**
     * 添加
     * @param goodsEntity
     * @return
     */
    Integer insertGoods(GoodsEntity goodsEntity);

}
