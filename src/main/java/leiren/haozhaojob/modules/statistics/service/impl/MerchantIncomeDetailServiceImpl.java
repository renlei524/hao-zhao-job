package leiren.haozhaojob.modules.statistics.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.statistics.dao.MerchantIncomeDetailDao;
import leiren.haozhaojob.modules.statistics.entity.MerchantIncomeDetailEntity;
import leiren.haozhaojob.modules.statistics.service.MerchantIncomeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("merchantIncomeDetailService")
public class MerchantIncomeDetailServiceImpl extends ServiceImpl<MerchantIncomeDetailDao,MerchantIncomeDetailEntity> implements MerchantIncomeDetailService {

    @Autowired
    MerchantService merchantService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public PageUtils queryPage(Map<String, Object> params){
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

        params.put("current",((new Query<MerchantIncomeDetailEntity>(params).getPage().getCurrent()-1)*new Query<MerchantIncomeDetailEntity>(params).getPage().getSize()));
        params.put("size",new Query<MerchantIncomeDetailEntity>(params).getPage().getSize());
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        List<MerchantEntity> merchantEntityList = merchantService.getMerchantByArea(params);

        List<MerchantIncomeDetailEntity> merchantIncomeDetailEntityList = new ArrayList<>();

        for (MerchantEntity merchantEntity : merchantEntityList) {
            params.put("merchantId",merchantEntity.getId());
            MerchantIncomeDetailEntity merchantIncomeDetailEntity =baseMapper.getMerchantIncomeDetailBymerchantId(params);
            if(merchantIncomeDetailEntity == null)
            {
                merchantIncomeDetailEntity = new MerchantIncomeDetailEntity();
                merchantIncomeDetailEntity.setIncomePen(0);
                merchantIncomeDetailEntity.setExpenditurePens(0);
            }
            if (merchantIncomeDetailEntity.getTotalIncome() != null){
                merchantIncomeDetailEntity.setTotalIncome(merchantIncomeDetailEntity.getTotalIncome() / 100);
            }
            if (merchantIncomeDetailEntity.getTotalExpenditure() != null){
                merchantIncomeDetailEntity.setTotalExpenditure(merchantIncomeDetailEntity.getTotalExpenditure() / 100);
            }
            merchantIncomeDetailEntity.setMerchantName(merchantEntity.getMerchantName());
            merchantIncomeDetailEntity.setSysUserName(merchantEntity.getSalesmanName());
            merchantIncomeDetailEntity.setTypeName(merchantEntity.getTypeName());
            merchantIncomeDetailEntity.setCreateTime(merchantEntity.getCreateTime());
            merchantIncomeDetailEntity.setDeptName(merchantEntity.getAgentName());
            merchantIncomeDetailEntityList.add(merchantIncomeDetailEntity);
        }

        //初始化对象
        Page<MerchantIncomeDetailEntity> page= new Page<>();
        //获取数据
        page.setRecords(merchantIncomeDetailEntityList);
        //获取每页数据数量
        page.setSize(new Query<MerchantIncomeDetailEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<MerchantIncomeDetailEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(merchantService.getMerchantByAreaTotal(params));

        return new PageUtils(page);
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
