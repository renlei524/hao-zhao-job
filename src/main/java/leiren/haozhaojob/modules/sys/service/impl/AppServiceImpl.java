package leiren.haozhaojob.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.sys.dao.AppDao;
import leiren.haozhaojob.modules.sys.entity.AppEntity;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.AppService;
import leiren.haozhaojob.modules.sys.service.SysUserService;
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
