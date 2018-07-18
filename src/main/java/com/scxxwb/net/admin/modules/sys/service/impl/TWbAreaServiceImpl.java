package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.sys.dao.TWbAreaDao;
import com.scxxwb.net.admin.modules.sys.entity.TWbAreaEntity;
import com.scxxwb.net.admin.modules.sys.service.TWbAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("tWbAreaService")
public class TWbAreaServiceImpl extends ServiceImpl<TWbAreaDao, TWbAreaEntity> implements TWbAreaService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TWbAreaEntity> page = this.selectPage(
                new Query<TWbAreaEntity>(params).getPage(),
                new EntityWrapper<TWbAreaEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public List<TWbAreaEntity> infoList(Map<String, Object> params, String parentCode) {
        List<TWbAreaEntity> twbAreaList =
                this.selectList(new EntityWrapper<TWbAreaEntity>()
                        .eq(StringUtils.isNotBlank(parentCode),"parent_code", parentCode)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
        return  twbAreaList;
    }

    @Override
    public TWbAreaEntity selectByAreaCode(Integer areaCode) {
        return baseMapper.selectByAreaCode(areaCode);
    }


    @Override
    @DataFilter(subDept = true, user = false)
    public List<TWbAreaEntity> queryList(Map<String, Object> params){
        List<TWbAreaEntity> deptList =
                this.selectList(new EntityWrapper<TWbAreaEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
        return deptList;
    }
}
