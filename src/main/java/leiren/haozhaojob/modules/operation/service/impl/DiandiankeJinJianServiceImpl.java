package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.dao.DiandiankeJinJianDao;
import leiren.haozhaojob.modules.operation.entity.DiandiankeJinJianEntity;
import leiren.haozhaojob.modules.operation.service.DiandiankeJinJianService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("jinJianService")
public class DiandiankeJinJianServiceImpl extends ServiceImpl<DiandiankeJinJianDao, DiandiankeJinJianEntity> implements DiandiankeJinJianService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiandiankeJinJianEntity> page = this.selectPage(
                new Query<DiandiankeJinJianEntity>(params).getPage(),
                new EntityWrapper<DiandiankeJinJianEntity>()
        );

        return new PageUtils(page);
    }
}
