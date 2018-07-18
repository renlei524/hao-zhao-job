package com.scxxwb.net.admin.modules.operation.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.modules.operation.dao.TUserWeiBaoDao;
import com.scxxwb.net.admin.modules.operation.entity.TUserWeiBaoEntity;
import com.scxxwb.net.admin.modules.operation.service.TUserWeiBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("TUserWeiBaoService")
public class TUserWeiBaoServiceImpl extends ServiceImpl<TUserWeiBaoDao, TUserWeiBaoEntity> implements TUserWeiBaoService {
    @Autowired
    protected KafkaProducer kafkaProducer;


}
