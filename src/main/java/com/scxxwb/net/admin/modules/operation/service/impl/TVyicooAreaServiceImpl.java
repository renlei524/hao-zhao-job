package com.scxxwb.net.admin.modules.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TVyicooAreaDao;
import com.scxxwb.net.admin.modules.operation.entity.TVyicooAreaEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooAreaService;


@Service("tVyicooAreaService")
public class TVyicooAreaServiceImpl extends ServiceImpl<TVyicooAreaDao, TVyicooAreaEntity> implements TVyicooAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TVyicooAreaEntity> page = this.selectPage(
                new Query<TVyicooAreaEntity>(params).getPage(),
                new EntityWrapper<TVyicooAreaEntity>()
        );

        return new PageUtils(page);
    }

}
