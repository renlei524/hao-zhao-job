package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
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
public interface SalesmanKPIService extends IService<SalesmanKPIEntity> {

    List<SalesmanKPIEntity> queryPage(Map<String, Object> params);

}
