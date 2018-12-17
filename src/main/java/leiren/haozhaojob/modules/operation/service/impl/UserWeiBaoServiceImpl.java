package leiren.haozhaojob.modules.operation.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.dao.UserWeiBaoDao;
import leiren.haozhaojob.modules.operation.entity.UserWeiBaoEntity;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import leiren.haozhaojob.modules.operation.service.UserWeiBaoService;
import leiren.haozhaojob.modules.operation.service.WbUserService;
import leiren.haozhaojob.modules.statistics.service.MerchantDayIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("UserWeiBaoService")
public class UserWeiBaoServiceImpl extends ServiceImpl<UserWeiBaoDao, UserWeiBaoEntity> implements UserWeiBaoService {
    @Autowired
    protected KafkaProducer kafkaProducer;

    @Autowired
    WbUserService wbUserService;

    @Autowired
    MerchantDayIncomeService merchantDayIncomeService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        String mobile = (String)params.get("mobile");
//        String userName = (String)params.get("userName");
//        Page<UserWeiBaoEntity> page = this.selectPage(
//                new Query<UserWeiBaoEntity>(params).getPage(),
//                new EntityWrapper<UserWeiBaoEntity>()
//                .addFilterIfNeed((StringUtils.isNotBlank(mobile) || StringUtils.isNotBlank(userName)),"user_id in (select id from t_wb_user where "
//                        + (StringUtils.isNotBlank(userName) ? "real_name like '%" + userName + "%'" : "1 = 1") + " and "
//                        + (StringUtils.isNotBlank(mobile) ? "mobile like '%" + mobile + "%'" : "1 = 1") +" )")
//        );
        //获取查询条件
        params.put("current",(new Query<UserWeiBaoEntity>(params).getPage().getCurrent()-1)*new Query<UserWeiBaoEntity>(params).getPage().getSize());
        params.put("size",new Query<UserWeiBaoEntity>(params).getPage().getSize());
        //初始化对象
        Page<UserWeiBaoEntity> page= new Page<>();
        //获取数据
        page.setRecords(baseMapper.selectUserWeiBao(params));
        //获取每页数据数量
        page.setSize(new Query<UserWeiBaoEntity>(params).getPage().getSize());
        //获取页码
        page.setCurrent(new Query<UserWeiBaoEntity>(params).getPage().getCurrent());
        //获取总页数
        page.setTotal(baseMapper.totalUserWeiBao(params));

        for (UserWeiBaoEntity userWeiBaoEntity : page.getRecords()) {
            userWeiBaoEntity.setReceiveWB(userWeiBaoEntity.getReceiveWB().divide(new BigDecimal("100")));
            userWeiBaoEntity.setWeibaoWithdrawals(userWeiBaoEntity.getWeibaoWithdrawals().divide(new BigDecimal("100")));
            userWeiBaoEntity.setUseWB(userWeiBaoEntity.getUseWB().divide(new BigDecimal("100")));
            WbUserEntity wbUserEntity = wbUserService.selectById(userWeiBaoEntity.getUserId());
            if (wbUserEntity != null){
                userWeiBaoEntity.setUserName(wbUserEntity.getRealName());
                userWeiBaoEntity.setMobile(wbUserEntity.getMobile());
            }
        }

        return new PageUtils(page);
    }
}
