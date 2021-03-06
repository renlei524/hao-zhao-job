package leiren.haozhaojob.modules.statistics.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.statistics.dao.MerchantIncomeTotalDao;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeTotalEntity;
import leiren.haozhaojob.modules.statistics.service.MerchantIncomeTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("merchantIncomeTotalService")
public class MerchantIncomeTotalServiceImpl extends ServiceImpl<MerchantIncomeTotalDao,MerchantIncomeTotalEntity> implements MerchantIncomeTotalService {

    @Autowired
    MerchantService merchantService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public MerchantIncomeTotalEntity queryPage(Map<String, Object> params) {
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        if (startTime != null && startTime.length() > 5){
            startTime = startTime + " 00:00:00";
        }
        else {
            startTime = (sdf.format(new Date())).substring(0,8) + "01 00:00:00";
        }
        if (endTime != null && endTime.length() > 5){
            endTime = endTime + " 23:59:59";
        }
        else{
            //获取当前日期
            endTime = (sdf.format(new Date())).substring(0,10) + " 23:59:59";
        }
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        //根据地区条件和商户条件查询出商户信息
        List<MerchantEntity> merchantEntityList = merchantService.getMerchantByArea(params);

        //初始化对象
        List<Integer> merchantId =new ArrayList<>();
        for (MerchantEntity merchantEntity : merchantEntityList){
            merchantId.add(merchantEntity.getId());
        }
        if (merchantId.size() == 0){
            merchantId.add(0);
        }
        params.put("merchantId",merchantId);
        //查询收入支出统计
        MerchantIncomeTotalEntity merchantIncomeTotalEntity =baseMapper.getMerchantIncomeTotalByArea(params);
        if (merchantIncomeTotalEntity.getTotalIncome() != null){
            merchantIncomeTotalEntity.setTotalIncome(merchantIncomeTotalEntity.getTotalIncome() / 100);
        }
        if (merchantIncomeTotalEntity.getTotalExpenditure() != null){
            merchantIncomeTotalEntity.setTotalExpenditure(merchantIncomeTotalEntity.getTotalExpenditure() / 100);
        }

        //查询支付宝微信收入统计
        MerchantIncomeTotalEntity merchantIncomeTotals =baseMapper.getMerchantIncomeTotalByAlipay(params);
        if(merchantIncomeTotals != null)
        {
            if (merchantIncomeTotals.getWeChatIncome() != null)
                merchantIncomeTotalEntity.setWeChatIncome(merchantIncomeTotals.getWeChatIncome() / 100);
            if (merchantIncomeTotals.getAlipayIncome() != null)
                merchantIncomeTotalEntity.setAlipayIncome(merchantIncomeTotals.getAlipayIncome() / 100);
            if (merchantIncomeTotals.getWeiBaoIncome() != null)
                merchantIncomeTotalEntity.setWeiBaoIncome(merchantIncomeTotals.getWeiBaoIncome() / 100);
            if (merchantIncomeTotals.getWeiBaoNumberOfPen() != null)
                merchantIncomeTotalEntity.setWeiBaoNumberOfPen(merchantIncomeTotals.getWeiBaoNumberOfPen());
        }
        return merchantIncomeTotalEntity;
    }

    /**
     * String转Date
     * @param dateStr
     * @return
     */
    private Date StringToDate (String dateStr){
        if (dateStr == null)
        {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            Date date = sf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
