package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.dao.VyicooCategoryDao;
import leiren.haozhaojob.modules.operation.entity.VyicooCategoryEntity;
import leiren.haozhaojob.modules.operation.service.VyicooCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("tVYiCooCategoryService")
public class VyicooCategoryServiceImpl extends ServiceImpl<VyicooCategoryDao, VyicooCategoryEntity> implements VyicooCategoryService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
//    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String category = (String)params.get("categoryName");
        Page<VyicooCategoryEntity> page = this.selectPage(
                new Query<VyicooCategoryEntity>(params).getPage(),
                new EntityWrapper<VyicooCategoryEntity>()
                        .like(StringUtils.isNotBlank(category),"category", category)
//                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "merchant_id in ("+ages+")")
        );

        return new PageUtils(page);
    }
}
