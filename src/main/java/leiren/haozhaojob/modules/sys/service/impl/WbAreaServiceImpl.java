package leiren.haozhaojob.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.sys.dao.WbAreaDao;
import leiren.haozhaojob.modules.sys.entity.WbAreaEntity;
import leiren.haozhaojob.modules.sys.service.WbAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("tWbAreaService")
public class WbAreaServiceImpl extends ServiceImpl<WbAreaDao, WbAreaEntity> implements WbAreaService {
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    protected WbAreaService tWbAreaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WbAreaEntity> page = this.selectPage(
                new Query<WbAreaEntity>(params).getPage(),
                new EntityWrapper<WbAreaEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public List<WbAreaEntity> infoList(Map<String, Object> params, String parentCode) {
        List<WbAreaEntity> twbAreaList =
                this.selectList(new EntityWrapper<WbAreaEntity>()
                        .eq(StringUtils.isNotBlank(parentCode),"parent_code", parentCode)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
        return  twbAreaList;
    }

    @Override
    public WbAreaEntity selectByAreaCode(Integer areaCode) {
        return baseMapper.selectByAreaCode(areaCode);
    }


    @Override
    @DataFilter(subDept = true, user = false)
    public List<WbAreaEntity> queryList(Map<String, Object> params){
        List<WbAreaEntity> deptList =
                this.selectList(new EntityWrapper<WbAreaEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
        return deptList;
    }

    public void updateAreaByTWBArea(Map<String, Object> params, WbAreaEntity tWbArea) {
        WbAreaEntity tWbAreaEntity = tWbAreaService.selectById(tWbArea.getId());
        if (!tWbAreaEntity.getAreaCode().equals(tWbArea.getAreaCode())){
            for (WbAreaEntity tWbAreaEntitys : tWbAreaService.infoList(params,tWbAreaEntity.getAreaCode().toString())){
                tWbAreaEntitys.setParentCode(tWbArea.getAreaCode());
                tWbAreaService.updateById(tWbAreaEntitys);
            }
        }
        tWbAreaService.updateById(tWbArea);//全部更新
    }
}
