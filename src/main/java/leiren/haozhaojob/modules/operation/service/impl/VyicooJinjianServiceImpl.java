package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.modules.operation.dao.VyicooJinjianDao;
import leiren.haozhaojob.modules.operation.entity.VyicooJinjianEntity;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.service.VyicooJinjianService;
import org.springframework.transaction.annotation.Transactional;


@Service("tVyicooJinjianService")
public class VyicooJinjianServiceImpl extends ServiceImpl<VyicooJinjianDao, VyicooJinjianEntity> implements VyicooJinjianService {
    @Autowired
    VyicooJinjianDao tVyicooJinjianDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        boolean flag = StringUtils.isNotBlank((String)params.get("name"));
        //获取查询过滤参数
        String filter = StringUtils.trim((String)params.get("name"));
        Page<VyicooJinjianEntity> page = this.selectPage(
                new Query<VyicooJinjianEntity>(params).getPage(),
                new EntityWrapper<VyicooJinjianEntity>()
                        .like(flag, "name", filter)
                        .or().like(flag, "realname", filter)
                        .or().like(flag, "mobile", filter)
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
