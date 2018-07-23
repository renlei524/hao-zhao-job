package com.scxxwb.net.admin.modules.operation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TVyicooJinjianDao;
import com.scxxwb.net.admin.modules.operation.entity.TVyicooJinjianEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooJinjianService;
import org.springframework.transaction.annotation.Transactional;


@Service("tVyicooJinjianService")
public class TVyicooJinjianServiceImpl extends ServiceImpl<TVyicooJinjianDao, TVyicooJinjianEntity> implements TVyicooJinjianService {
    @Autowired
    TVyicooJinjianDao tVyicooJinjianDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TVyicooJinjianEntity> page = this.selectPage(
                new Query<TVyicooJinjianEntity>(params).getPage(),
                new EntityWrapper<TVyicooJinjianEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateStatus(Map map) {
        map.put("mchId", map.get("mch_id"));
        tVyicooJinjianDao.updatestatus(map);
    }
}
