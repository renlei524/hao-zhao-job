package com.scxxwb.net.admin.modules.operation.service.impl;

import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.entity.TWbUserEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import com.scxxwb.net.admin.modules.operation.service.TWbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;

import com.scxxwb.net.admin.modules.operation.dao.TWbOrderDao;
import com.scxxwb.net.admin.modules.operation.entity.TWbOrderEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbOrderService;


@Service("tWbOrderService")
public class TWbOrderServiceImpl extends ServiceImpl<TWbOrderDao, TWbOrderEntity> implements TWbOrderService {
    @Autowired
    TWbUserService tWbUserService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    protected KafkaProducer kafkaProducer;

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
        params.put("current",(new Query<TWbOrderEntity>(params).getPage().getCurrent()-1)*new Query<TWbOrderEntity>(params).getPage().getSize());
        params.put("size",new Query<TWbOrderEntity>(params).getPage().getSize());
        params.put("ages",ages.size() == 0 ? null : ages);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        //初始化对象
        Page<TWbOrderEntity> page= new Page<>();
        //获取数据
        page.setRecords(baseMapper.selectByUserName(params));
        //获取每页数据数量
        page.setSize(new Query<TWbOrderEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<TWbOrderEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.total(params));
        //根据id获取name值
        for(TWbOrderEntity twbOrderEntity : page.getRecords()){
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
        params.put("current",(new Query<TWbOrderEntity>(params).getPage().getCurrent()-1)*new Query<TWbOrderEntity>(params).getPage().getSize());
        params.put("size",new Query<TWbOrderEntity>(params).getPage().getSize());
        //初始化对象
        Page<TWbOrderEntity> page= new Page<>();
        //获取数据
        page.setRecords(baseMapper.selectByMerchantId(params));
        //获取每页数据数量
        page.setSize(new Query<TWbOrderEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<TWbOrderEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.totalByMerchantId(params));
        //根据id获取name值
        for(TWbOrderEntity twbOrderEntity : page.getRecords()){
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
    public Double totalIncomeByMerchantId(Integer merchantId) {
        Double num = baseMapper.totalIncomeByMerchantId(merchantId);
        return num != null ? (num / 100) : 0.00;
    }

}
