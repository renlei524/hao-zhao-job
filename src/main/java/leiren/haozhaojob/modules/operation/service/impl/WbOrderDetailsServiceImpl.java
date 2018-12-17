package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.service.WbOrderDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.WbOrderDetailsDao;
import leiren.haozhaojob.modules.operation.entity.WbOrderDetailsEntity;


@Service("wbOrderDetailsService")
public class WbOrderDetailsServiceImpl extends ServiceImpl<WbOrderDetailsDao, WbOrderDetailsEntity> implements WbOrderDetailsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String orderId = (String)params.get("orderId");
        Page<WbOrderDetailsEntity> page = this.selectPage(
                new Query<WbOrderDetailsEntity>(params).getPage(),
                new EntityWrapper<WbOrderDetailsEntity>()
                .eq(StringUtils.isNotBlank(orderId),"order_id",orderId)
        );
        for (WbOrderDetailsEntity wbOrderDetailsEntity : page.getRecords()){
            wbOrderDetailsEntity.setPrice(wbOrderDetailsEntity.getPrice() / 100);
            wbOrderDetailsEntity.setFinalPrice(wbOrderDetailsEntity.getFinalPrice() / 100);
        }

        return new PageUtils(page);
    }

}
