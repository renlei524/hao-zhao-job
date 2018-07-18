package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.sys.dao.AppDao;
import com.scxxwb.net.admin.modules.sys.entity.AppEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.AppService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppDao, AppEntity> implements AppService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AppEntity> page = this.selectPage(
                new Query<AppEntity>(params).getPage(),
                new EntityWrapper<AppEntity>()
        );

        for(AppEntity appEntity : page.getRecords())
        {
            SysUserEntity sysUserEntity = sysUserService.selectById(appEntity.getUserId());
            if (sysUserEntity != null)
            {
                appEntity.setUserName(sysUserEntity.getRealName());
            }
        }

        return new PageUtils(page);
    }
}
