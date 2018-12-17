package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.modules.operation.dao.WbUserDao;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.UserWeiBaoEntity;
import leiren.haozhaojob.modules.operation.entity.WbOrderEntity;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.modules.operation.service.UserWeiBaoService;
import leiren.haozhaojob.modules.operation.service.WbOrderService;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.service.WbUserService;
import org.springframework.transaction.annotation.Transactional;


@Service("tWbUserService")
public class WbUserServiceImpl extends ServiceImpl<WbUserDao, WbUserEntity> implements WbUserService {
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    private UserWeiBaoService tUserWeiBaoService;
    @Autowired
    private WbOrderService tWbOrderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private WbUserDao wbUserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String realName = (String)params.get("realName");
        boolean flag = StringUtils.isNotBlank(realName);
        Page<WbUserEntity> page = this.selectPage(
                new Query<WbUserEntity>(params).getPage(),
                new EntityWrapper<WbUserEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                        .andNew(flag, "real_name LIKE '%" + realName +"%'")
                        .or(flag, "mobile LIKE '%" + realName + "%'")
                        .or(flag, "id_card LIKE '%" + realName + "%'")
        );
        List<WbUserEntity> tWbUserEntityList = page.getRecords();
        for (WbUserEntity tWbUserEntity : tWbUserEntityList){
            //获取用户id
            try {
                Integer userId = tWbUserEntity.getId();
                Map<String, Date> map = tWbOrderService.selectTWBUserRecconsumptionTime(userId);
                if (map != null) {
                    //获取最近消费时间
                    Date date = map.get("rec_consumption_time");
                    //添加最近消费时间
                    tWbUserEntity.setRecConsumptionTime(date);
                    //获取消费总金额和最近消费商户id
                    WbOrderEntity tWbOrderEntity = tWbOrderService.selectTWBUserTotalAndArea(userId);
                    //添加消费总金额
                    tWbUserEntity.setTotalConsumption(tWbOrderEntity.getTotalConsumption() * 1.0 / 100);
                    //获取最近消费商户id
                    Integer merchantId = tWbOrderEntity.getRecConsumptionMerchantId();
                    //根据商户id查询商户信息
                    MerchantEntity merchantEntity = merchantService.selectById(merchantId);
                    if (merchantEntity != null){
                        //获取客户经理id
                        Integer salesManId = merchantEntity.getSalesman();
                        //根据客户经理id查询客户经理信息
                        SysUserEntity sysUserEntity = sysUserService.selectById(salesManId);
                        if(sysUserEntity != null) {
                            //添加城市经理名称
                            tWbUserEntity.setSalesMan(sysUserEntity.getRealName());
                            //添加城市经理电话
                            tWbUserEntity.setSalesManTel(sysUserEntity.getMobile());
                        }
                    //添加最近消费地区名称
                    tWbUserEntity.setRecConsumptionArea(merchantEntity.getAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page.setRecords(tWbUserEntityList);
        return new PageUtils(page);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public List<WbUserEntity> queryList(Map<String, Object> map) {
        List<WbUserEntity> userList =
                this.selectList(new EntityWrapper<WbUserEntity>()
                .addFilterIfNeed(map.get(Constant.SQL_FILTER) != null, (String)map.get(Constant.SQL_FILTER)));
        return  userList;
    }

    @Override
    @Transactional
    public Boolean saveUser(WbUserEntity tWbUser) {
        boolean result = this.insert(tWbUser);
        if(result){
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", tWbUser.getMobile());
            WbUserEntity tWbUserEntity = (this.selectByMap(map)).get(0);
            UserWeiBaoEntity tUserWeiBaoEntity = new UserWeiBaoEntity();
            tUserWeiBaoEntity.setUserId(tWbUserEntity.getId());
            result = tUserWeiBaoService.insert(tUserWeiBaoEntity);
        }

        return result;
    }

    @Override
    public List<Integer> selectUserIdByUserNameOrMobile(String search) {
        return wbUserDao.selectUserIdByUserNameOrMobile(search);
    }

    @Override
    public WbUserEntity selectUserNameAndMobileByUserId(Integer id) {
        return wbUserDao.selectUserNameAndMobileByUserId(id);
    }

}
