package leiren.haozhaojob.modules.operation.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.operation.entity.GoodsCheckEntity;

import java.util.Map;

/**
 * 商品信息审核
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
public interface GoodsCheckDao extends BaseMapper<GoodsCheckEntity> {

    /**
     * 批量操作
     * @param params
     * @return
     */
    Boolean batchOperation(Map<String, Object> params);

}
