package leiren.haozhaojob.modules.statistics.controller;

import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.statistics.entity.SalesmanKPIEntity;
import leiren.haozhaojob.modules.statistics.service.SalesmanKPIService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务员KPI
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
@RestController
@RequestMapping("statistics/salesmanKPI")
public class SalesmanKPIController extends AbstractController {

    @Autowired
    SalesmanKPIService salesmanKPIService;

    List<SalesmanKPIEntity> salesmanKPIEntityList = new ArrayList<>();

    /**
     * 列表
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:salesmanKPI:list")
    public R list(@RequestParam Map<String, Object> params)
    {
        List<SalesmanKPIEntity> salesmanKPIList = salesmanKPIService.queryPage(params);
        this.salesmanKPIEntityList = salesmanKPIList;
        return R.ok().put("salesmanKPIList",salesmanKPIList);
    }

    @RequestMapping("/show")
    @RequiresPermissions("statistics:salesmanKPI:list")
    public R show(@RequestParam Map<String, Object> params)
    {
        return R.ok().put("salesmanKPIEntityList",this.salesmanKPIEntityList);
    }

}
