package com.scxxwb.net.admin.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.operation.dao.JinJianDao;
import com.scxxwb.net.admin.modules.operation.entity.JinJianEntity;
import com.scxxwb.net.admin.modules.operation.service.JinJianService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("jinJianService")
public class JinJianServiceImpl extends ServiceImpl<JinJianDao, JinJianEntity> implements JinJianService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JinJianEntity> page = this.selectPage(
                new Query<JinJianEntity>(params).getPage(),
                new EntityWrapper<JinJianEntity>()
        );

        return new PageUtils(page);
    }
}
