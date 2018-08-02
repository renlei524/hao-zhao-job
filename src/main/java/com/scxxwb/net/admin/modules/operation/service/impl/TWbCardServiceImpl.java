package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TWbCardDao;
import com.scxxwb.net.admin.modules.operation.entity.TWbCardEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbCardService;


@Service("tWbCardService")
public class TWbCardServiceImpl extends ServiceImpl<TWbCardDao, TWbCardEntity> implements TWbCardService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    protected MerchantService merchantService;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list = null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs = num.split(",");
            list=Arrays.asList(strs);
        }
        List<Integer> merchantIds = merchantService.getMerchant(list);
        String merchantId = StringUtils.join(merchantIds,',');
        String cardContent = (String)params.get("cardContent");
        String creatorType = (String)params.get("creatorType");
        String type = (String)params.get("type");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        if (startTime != null){
            startTime = startTime + " 00:00:00";
        }
        if (endTime != null){
            endTime = endTime + " 23:59:59";
        }
        Page<TWbCardEntity> page = this.selectPage(
                new Query<TWbCardEntity>(params).getPage(),
                new EntityWrapper<TWbCardEntity>()
                        .like(StringUtils.isNotBlank(cardContent),"card_content", cardContent)
                        .in(StringUtils.isNotBlank(creatorType) && !creatorType.equals("-1"),"creator_type",creatorType)
                        .in(StringUtils.isNotBlank(type) && !type.equals("-1"),"type",type)
                        .between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime),"create_date",startTime,endTime)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "creator_id in ("+merchantId+")")
        );
        return new PageUtils(page);
    }
}
