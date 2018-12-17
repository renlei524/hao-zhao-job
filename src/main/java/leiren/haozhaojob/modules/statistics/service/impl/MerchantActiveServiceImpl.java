package leiren.haozhaojob.modules.statistics.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.utils.BigDecimalUtils;
import leiren.haozhaojob.common.utils.DateUtils;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.operation.service.WbOrderService;
import leiren.haozhaojob.modules.statistics.dao.MerchantActiveDao;
import leiren.haozhaojob.modules.statistics.entity.MerchantActiveEntity;
import leiren.haozhaojob.modules.statistics.service.MerchantActiveService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("merchantActiveService")
public class MerchantActiveServiceImpl extends ServiceImpl<MerchantActiveDao,MerchantActiveEntity> implements MerchantActiveService {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WbOrderService wbOrderService;
    @Autowired
    private MerchantActiveDao merchantActiveDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取查询参数商户名称
        String merchantName = (String)params.get("merchantName");
        //获取查询起始时间 d
        String startTime = (String)params.get("startTime");
        //处理查询起始时间参数字符串 d
        Date startTimeSearch = DateUtils.searchStartDateDeal(startTime);
        //获取查询终止时间 d
        String endTime = (String)params.get("endTime");
        //处理查询结束时间参数字符串 d
        Date endTimeSearch = DateUtils.searchEndDateDeal(endTime);
        params.replace("startTime", startTimeSearch);
        params.replace("endTime", endTimeSearch);
        Integer pageSize = Integer.valueOf((String)params.get("limit"));
        Integer pageNum = (Integer.valueOf((String)params.get("page"))-1) * pageSize;
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        int count = this.selectMerchantCountByName(merchantName);
        List<MerchantActiveEntity> merchantActiveEntityList = this.selectMerchantByMerchantNameAndDate(params);
        for (MerchantActiveEntity merchantActiveEntity : merchantActiveEntityList) {
            //转换现金金额
            merchantActiveEntity.setCashAmount(BigDecimalUtils.ObjectToBigDecimal(merchantActiveEntity.getCashAmount()));
            //转换维保金额
            merchantActiveEntity.setWeiBaoAmount(BigDecimalUtils.ObjectToBigDecimal(merchantActiveEntity.getWeiBaoAmount()));
            //转换优惠群金额
            merchantActiveEntity.setCouponAmount(BigDecimalUtils.ObjectToBigDecimal(merchantActiveEntity.getCouponAmount()));
            //添加开始时间
            merchantActiveEntity.setStartDate(startTimeSearch);
            //添加查询结束时间
            merchantActiveEntity.setEndDate(endTimeSearch);
        }
        Page<MerchantActiveEntity> page = new Page();
        page.setSize(pageSize);
        page.setCurrent(Integer.valueOf((String)params.get("page")));
        page.setTotal(count);
        page.setRecords(merchantActiveEntityList);
        return  new PageUtils(page);
    }

    @Override
    public Page<MerchantActiveEntity> selectMerchantByMerchantName(Map<String, Object> params, String name) {
        Page<MerchantActiveEntity> page = this.selectPage(
                new Query<MerchantActiveEntity>(params).getPage(),
                new EntityWrapper<MerchantActiveEntity>()
                        .like(StringUtils.isNotBlank(name), "merchant_name", name)
        );
        return page;
    }

    @Override
    public List<MerchantActiveEntity> selectMerchantByMerchantNameAndDate(Map<String, Object> params) {
        return merchantActiveDao.selectMerchantByMerchantNameAndDate(params);
    }

    @Override
    public int selectMerchantCountByName(String name) {
        int count = this.selectCount(
                new EntityWrapper<MerchantActiveEntity>()
                        .like(StringUtils.isNotBlank(name), "merchant_name", name)
        );
        return count;
    }
}
