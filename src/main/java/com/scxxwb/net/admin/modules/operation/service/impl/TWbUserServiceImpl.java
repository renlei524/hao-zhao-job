package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.entity.TUserWeiBaoEntity;
import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;
import com.scxxwb.net.admin.modules.operation.service.TUserWeiBaoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TWbUserDao;
import com.scxxwb.net.admin.modules.operation.entity.TWbUserEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbUserService;
import org.springframework.transaction.annotation.Transactional;


@Service("tWbUserService")
public class TWbUserServiceImpl extends ServiceImpl<TWbUserDao, TWbUserEntity> implements TWbUserService {
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    private TUserWeiBaoService tUserWeiBaoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String realName = (String)params.get("realName");
        Page<TWbUserEntity> page = this.selectPage(
                new Query<TWbUserEntity>(params).getPage(),
                new EntityWrapper<TWbUserEntity>()
                        .like(StringUtils.isNotBlank(realName),"real_name", realName)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public List<TWbUserEntity> queryList(Map<String, Object> map) {
        List<TWbUserEntity> userList =
                this.selectList(new EntityWrapper<TWbUserEntity>()
                .addFilterIfNeed(map.get(Constant.SQL_FILTER) != null, (String)map.get(Constant.SQL_FILTER)));
        return  userList;
    }

    @Override
    @Transactional
    public Boolean saveUser(TWbUserEntity tWbUser) {
        boolean result = this.insert(tWbUser);
        if(result){
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", tWbUser.getMobile());
            TWbUserEntity tWbUserEntity = (this.selectByMap(map)).get(0);
            TUserWeiBaoEntity tUserWeiBaoEntity = new TUserWeiBaoEntity();
            tUserWeiBaoEntity.setUserId(tWbUserEntity.getId());
            result = tUserWeiBaoService.insert(tUserWeiBaoEntity);
        }

        return result;
    }
}
