package leiren.haozhaojob.modules.sys.service.impl;

import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.sys.dao.SysNoticeDao;
import leiren.haozhaojob.modules.sys.service.SysNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.sys.entity.SysNoticeEntity;


@Service("tSysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity> implements SysNoticeService {
    @Autowired
    SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String isTop = (String)params.get("isTop");
        Page<SysNoticeEntity> page = this.selectPage(
                new Query<SysNoticeEntity>(params).getPage(),
                new EntityWrapper<SysNoticeEntity>()
                .addFilterIfNeed( StringUtils.isNotBlank(isTop) && !isTop.equals("-1"), "is_top", isTop)
                .like(StringUtils.isNotBlank(title),"title",title)

        );

        if(!page.getRecords().isEmpty()) {
            for (SysNoticeEntity sysNoticeEntity : page.getRecords()) {
                SysUserEntity sysUserEntity = sysUserService.selectById(sysNoticeEntity.getUserId());
                sysNoticeEntity.setUserName(sysUserEntity == null ? null : sysUserEntity.getUserName());
            }
        }

        return new PageUtils(page);
    }

}
