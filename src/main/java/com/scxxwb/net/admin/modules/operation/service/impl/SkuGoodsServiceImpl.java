package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.SkuGoodsDao;
import com.scxxwb.net.admin.modules.operation.entity.SkuGoodsEntity;
import com.scxxwb.net.admin.modules.operation.service.SkuGoodsService;


@Service("skuGoodsService")
public class SkuGoodsServiceImpl extends ServiceImpl<SkuGoodsDao, SkuGoodsEntity> implements SkuGoodsService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String goodsName = (String)params.get("goodsName");
        Page<SkuGoodsEntity> page = this.selectPage(
                new Query<SkuGoodsEntity>(params).getPage(),
                new EntityWrapper<SkuGoodsEntity>()
                        .like(StringUtils.isNotBlank(goodsName),"goods_name", goodsName)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
