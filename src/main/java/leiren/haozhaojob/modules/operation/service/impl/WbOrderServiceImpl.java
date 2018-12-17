package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.modules.operation.dao.WbOrderDao;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.WbOrderEntity;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.operation.service.WbUserService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.service.WbOrderService;


@Service("tWbOrderService")
public class WbOrderServiceImpl extends ServiceImpl<WbOrderDao, WbOrderEntity> implements WbOrderService {
    @Autowired
    WbUserService tWbUserService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    private WbOrderDao tWbOrderDao;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils selectByUserName(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list =null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs= num.split(",");
            list=Arrays.asList(strs);
        }
        List<Integer> ages =merchantService.getMerchant(list);

        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (startTime != null && startTime.length() > 5){
            startTime = startTime + " 00:00:00";
        }
        else {
            //获取当前日期
            startTime = (sdf.format(new Date())).substring(0,10) + " 00:00:00";
        }
        if (endTime != null && endTime.length() > 5){
            endTime = endTime + " 23:59:59";
        }
        else{
            endTime = (sdf.format(new Date())).substring(0,10) + " 23:59:59";
        }

        //获取查询条件
        params.put("current",(new Query<WbOrderEntity>(params).getPage().getCurrent()-1)*new Query<WbOrderEntity>(params).getPage().getSize());
        params.put("size",new Query<WbOrderEntity>(params).getPage().getSize());
        params.put("ages",ages.size() == 0 ? null : ages);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        //初始化对象
        Page<WbOrderEntity> page= new Page<>();
        //获取数据
        page.setRecords(baseMapper.selectByUserName(params));
        //获取每页数据数量
        page.setSize(new Query<WbOrderEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<WbOrderEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.total(params));
        //根据id获取name值
        for(WbOrderEntity twbOrderEntity : page.getRecords()){
            twbOrderEntity.setTotalAmount((twbOrderEntity.getTotalAmount() / 100.00));
            twbOrderEntity.setSettleAccounts((twbOrderEntity.getSettleAccounts() / 100.00));
            twbOrderEntity.setFinalAmount((twbOrderEntity.getFinalAmount() / 100.00));
            if (twbOrderEntity.getWbDeductible() != null){
                twbOrderEntity.setWbDeductible((twbOrderEntity.getWbDeductible() / 100.00));
            }else {
                twbOrderEntity.setWbDeductible(0.00);
            }
            if (twbOrderEntity.getCouponsOffset() != null){
                twbOrderEntity.setCouponsOffset((twbOrderEntity.getCouponsOffset() / 100.00));
            }else {
                twbOrderEntity.setCouponsOffset(0.00);
            }
            MerchantEntity merchantEntity =merchantService.selectById(twbOrderEntity.getMerchantId());
            if (merchantEntity != null){
                twbOrderEntity.setMerchantName(merchantEntity.getMerchantName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    public PageUtils selectByMerchantId(Map<String, Object> params) {

        //获取查询条件
        params.put("current",(new Query<WbOrderEntity>(params).getPage().getCurrent()-1)*new Query<WbOrderEntity>(params).getPage().getSize());
        params.put("size",new Query<WbOrderEntity>(params).getPage().getSize());
        //初始化对象
        Page<WbOrderEntity> page= new Page<>();
        //获取数据
        page.setRecords(baseMapper.selectByMerchantId(params));
        //获取每页数据数量
        page.setSize(new Query<WbOrderEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<WbOrderEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.totalByMerchantId(params));
        //根据id获取name值
        for(WbOrderEntity twbOrderEntity : page.getRecords()){
            twbOrderEntity.setTotalAmount((twbOrderEntity.getTotalAmount() / 100.00));
            if (twbOrderEntity.getWbDeductible() != null){
                twbOrderEntity.setWbDeductible((twbOrderEntity.getWbDeductible() / 100.00));
            }else {
                twbOrderEntity.setWbDeductible(0.00);
            }
        }

        return new PageUtils(page);
    }
    @Override
    public WbOrderEntity selectTWBUserTotalAndArea(Integer userId) {
        return tWbOrderDao.selectTWBUserTotalAndArea(userId);
    }

    @Override
    public Map<String, Date> selectTWBUserRecconsumptionTime(Integer userId) {
        return tWbOrderDao.selectTWBUserRecconsumptionTime(userId);
    }

    public Double totalIncomeByMerchantId(Integer merchantId) {
        Double num = baseMapper.totalIncomeByMerchantId(merchantId);
        return num != null ? (num / 100) : 0.00;
    }

    @Override
    public Map<String, Object> selectCashAmountByMerchantId(Date startTime, Date endTime, Integer merchantId) {
        return tWbOrderDao.selectCashAmountByMerchantId(startTime, endTime, merchantId);
    }

    @Override
    public Integer selectWeiBaoAmountByMerchantId(Date startTime, Date endTime, Integer merchantId) {
        return tWbOrderDao.selectWeiBaoAmountByMerchantId(startTime, endTime, merchantId);
    }

    @Override
    public int selectOrderDetailsByMerchantId(Map<String, Object> params) {
        //获取查询参数
        String merchantId = (String)params.get("id");
        String startTime = (String)params.get("startTime");
        //处理查询起始时间参数字符串 d
        Date startTimeSearch = new Date(Long.parseLong(startTime));
        String endTime = (String)params.get("endTime");
        //处理查询结束时间参数字符串 d
        Date endTimeSearch = new Date(Long.parseLong(endTime));
        //根据商户id查询订单
        int count = this.selectCount( new EntityWrapper<WbOrderEntity>()
                .eq(StringUtils.isNotBlank(merchantId), "merchant_id", merchantId)
                .between("create_time", startTimeSearch, endTimeSearch)
                .addFilter("status IN (1,2,3)")
        );
        return count;
    }

    @Override
    public List<WbOrderEntity> selectWBOrderByMerchantIdAndDate(Map<String, Object> params) {
        return tWbOrderDao.selectWBOrderByMerchantIdAndDate(params);
    }

    @Override
    public PageUtils selectWBOrder(Map<String, Object> params) {
        //获取查询参数
        String merchantId = (String)params.get("id");
        String startTime = (String)params.get("startTime");
        //处理查询起始时间参数字符串 d
        Date startTimeSearch = new Date(Long.parseLong(startTime));
        String endTime = (String)params.get("endTime");
        //处理查询结束时间参数字符串 d
        Date endTimeSearch = new Date(Long.parseLong(endTime));
        params.replace("startTime", startTimeSearch);
        params.replace("endTime", endTimeSearch);
        Integer pageSize = Integer.valueOf((String)params.get("limit"));
        Integer pageNum = (Integer.valueOf((String)params.get("page"))-1) * pageSize;
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        Long count = this.selectOrderCountsByMerchantId(params);
        List<WbOrderEntity> wbOrderEntityList = this.selectWBOrderByMerchantIdAndDate(params);
        if (!CollectionUtils.isEmpty(wbOrderEntityList)) {
            for (WbOrderEntity wbOrderEntity : wbOrderEntityList) {
                wbOrderEntity.setWbDeductible(wbOrderEntity.getWbDeductible()/ 100);
                //根据用户id查询用户名称
                WbUserEntity wbUserEntity = tWbUserService.selectById(wbOrderEntity.getUserId());
                if (wbUserEntity != null) {
                    wbOrderEntity.setUserName(wbUserEntity.getRealName());
                }
            }
        }
        Page<WbOrderEntity> page = new Page();
        page.setSize(pageSize);
        page.setCurrent(Integer.valueOf((String)params.get("page")));
        if (count == null) {
            count = 0L;
        }
        page.setTotal(Integer.valueOf(String.valueOf(count)));
        page.setRecords(wbOrderEntityList);

        return  new PageUtils(page);
    }

    @Override
    public Long selectOrderCountsByMerchantId(Map<String, Object> params) {
        return tWbOrderDao.selectOrderCountsByMerchantId(params);
    }

}
