package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.GoodsDao;
import com.scxxwb.net.admin.modules.operation.entity.GoodsEntity;
import com.scxxwb.net.admin.modules.operation.service.GoodsService;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    MerchantService merchantService;
    //修改分页查询方法 yh
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list =null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs= num.split(",");
            list=Arrays.asList(strs);
        }
        List<Integer> lists =merchantService.getMerchant(list);
        String ages=StringUtils.join(lists,",");
        String goodsName = (String)params.get("goodsName");
        Page<GoodsEntity> page = this.selectPage(
                new Query<GoodsEntity>(params).getPage(),
                new EntityWrapper<GoodsEntity>()
                        .like(StringUtils.isNotBlank(goodsName),"goods_name", goodsName)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "merchant_id in ("+ages+")")
        );

        return new PageUtils(page);
    }
}
