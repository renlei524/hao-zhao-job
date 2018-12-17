package leiren.haozhaojob.modules.statistics.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.modules.statistics.dao.SalesmanKPIDao;
import leiren.haozhaojob.modules.statistics.entity.SalesmanKPIEntity;
import leiren.haozhaojob.modules.statistics.service.SalesmanKPIService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("salesmanKPIService")
public class SalesmanKPIServiceImpl extends ServiceImpl<SalesmanKPIDao,SalesmanKPIEntity> implements SalesmanKPIService {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<SalesmanKPIEntity> queryPage(Map<String, Object> params) {
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

        return baseMapper.getSalesmanKPI(params);
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
