package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.modules.operation.dao.VyicooAreaDao;
import leiren.haozhaojob.modules.operation.entity.VyicooAreaEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.service.VyicooAreaService;


@Service("tVyicooAreaService")
public class VyicooAreaServiceImpl extends ServiceImpl<VyicooAreaDao, VyicooAreaEntity> implements VyicooAreaService {

    @Override
    public List<VyicooAreaEntity> queryPage(Integer areaCode) {
        List<VyicooAreaEntity> page =
                this.selectList(new EntityWrapper<VyicooAreaEntity>()
                        .eq(StringUtils.isNotBlank(areaCode.toString()),"area_parent_id", areaCode));
        return page;
    }

}
