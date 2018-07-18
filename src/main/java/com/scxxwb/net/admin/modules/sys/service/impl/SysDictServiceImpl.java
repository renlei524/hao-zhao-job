package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.sys.dao.SysDictDao;
import com.scxxwb.net.admin.modules.sys.entity.SysDictEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String type = (String)params.get("type");
        String value = (String)params.get("value");
        Page<SysDictEntity> page = this.selectPage(
                new Query<SysDictEntity>(params).getPage(),
                new EntityWrapper<SysDictEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
                    .like(StringUtils.isNotBlank(name),"type", type)
                    .like(StringUtils.isNotBlank(name),"value", value)
        );

        return new PageUtils(page);
    }

}
