package com.scxxwb.net.admin.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.operation.dao.TVYiCooCategoryDao;
import com.scxxwb.net.admin.modules.operation.entity.TVYiCooCategoryEntity;
import com.scxxwb.net.admin.modules.operation.service.TVYiCooCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("tVYiCooCategoryService")
public class TVYiCooCategoryServiceImpl extends ServiceImpl<TVYiCooCategoryDao, TVYiCooCategoryEntity> implements TVYiCooCategoryService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
//    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String category = (String)params.get("categoryName");
        Page<TVYiCooCategoryEntity> page = this.selectPage(
                new Query<TVYiCooCategoryEntity>(params).getPage(),
                new EntityWrapper<TVYiCooCategoryEntity>()
                        .like(StringUtils.isNotBlank(category),"category", category)
//                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "merchant_id in ("+ages+")")
        );

        return new PageUtils(page);
    }
}
