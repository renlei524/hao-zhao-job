package com.scxxwb.net.admin.modules.statistics.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.DateUtils;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import com.scxxwb.net.admin.modules.statistics.dao.MerchantDayIncomeDao;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantDayIncomeEntity;
import com.scxxwb.net.admin.modules.statistics.service.MerchantDayIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("merchantDayIncomeService")
public class MerchantDayIncomeServiceImpl extends ServiceImpl<MerchantDayIncomeDao,MerchantDayIncomeEntity> implements MerchantDayIncomeService {

    @Autowired
    MerchantService merchantService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String num =(String)params.get(Constant.SQL_FILTER);
        List<String> list =null;
        if (num != null){
            num = num.substring(13,num.length()-2);
            String [] strs= num.split(",");
            list=Arrays.asList(strs);
        }
        List<Integer> ages =merchantService.getMerchant(list);
        params.put("current",(new Query<MerchantDayIncomeEntity>(params).getPage().getCurrent()-1)*new Query<MerchantDayIncomeEntity>(params).getPage().getSize());
        params.put("size",new Query<MerchantDayIncomeEntity>(params).getPage().getSize());
        params.put("ages",ages.size() == 0 ? null : ages);

        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        if (startTime != null && startTime.length() > 5){
            startTime = startTime + " 00:00:00";
        }
        if (endTime != null && endTime.length() > 5){
            endTime = endTime + " 23:59:59";
        }
        if ( startTime == null || startTime.length() < 5 || endTime == null || endTime.length() < 5){
            //获取当前日期
            startTime = (sdf.format(new Date())).substring(0,10) + " 00:00:00";
            endTime = (sdf.format(new Date())).substring(0,10) + " 23:59:59";
        }
        params.put("startTime",startTime);
        params.put("endTime",endTime);

        //获取商户信息
        List<MerchantDayIncomeEntity> merchantDayIncomeEntityList = baseMapper.GetMerchant(params);

        MerchantDayIncomeEntity merchantDayIncome;

        int i = 1;
        //获取日交易数据
        for (MerchantDayIncomeEntity merchantDayIncomeEntity : merchantDayIncomeEntityList){
            params.put("merchantId",merchantDayIncomeEntity.getMerchantId());
            params.put("dateOfTransaction",DateUtils.format(merchantDayIncomeEntity.getDateOfTransaction(),"yyyy-MM-dd"));
            merchantDayIncome = baseMapper.GetTransactionAmount(params);
//            String yuanSheng = baseMapper.getYuanShi(params);
//            String Qita = baseMapper.getQiTa(params);
//            yuanSheng = yuanSheng == null ? "0" : yuanSheng;
//            Qita = Qita == null ? "0" : Qita;
//
//            merchantDayIncome.setTransactionAmount((merchantDayIncome.getTransactionAmount() + Integer.parseInt(yuanSheng) + Integer.parseInt(Qita)) * 0.01);
            merchantDayIncomeEntity.setTransactionAmount(merchantDayIncome.getTransactionAmount() / 100.0);
            merchantDayIncomeEntity.setTransactionPenNumber(merchantDayIncome.getTransactionPenNumber());
            merchantDayIncomeEntity.setWeChatPens(merchantDayIncome.getWeChatPens());
            merchantDayIncomeEntity.setAlipayPens(merchantDayIncome.getAlipayPens());
            merchantDayIncomeEntity.setTransactionNumber(merchantDayIncome.getTransactionNumber());
            merchantDayIncomeEntity.setCreditCardAmount(0.00);
            merchantDayIncomeEntity.setCreditCardNumber(0);
            merchantDayIncomeEntity.setAlipayReview(0);
            merchantDayIncomeEntity.setWeChatReview(0);
            merchantDayIncomeEntity.setNum(i);
            i++;
        }

        //初始化对象
        Page<MerchantDayIncomeEntity> page= new Page<>();
        //获取数据
        page.setRecords(merchantDayIncomeEntityList);
        //获取每页数据数量
        page.setSize(new Query<MerchantDayIncomeEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<MerchantDayIncomeEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.total(params));

        return new PageUtils(page);
    }

}
