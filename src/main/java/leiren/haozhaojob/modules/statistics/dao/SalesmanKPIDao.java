package leiren.haozhaojob.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.statistics.entity.SalesmanKPIEntity;

import java.util.List;
import java.util.Map;

/**
 * 业务员KPI
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
public interface SalesmanKPIDao extends BaseMapper<SalesmanKPIEntity> {

    List<SalesmanKPIEntity> getSalesmanKPI(Map<String, Object> params);

}
