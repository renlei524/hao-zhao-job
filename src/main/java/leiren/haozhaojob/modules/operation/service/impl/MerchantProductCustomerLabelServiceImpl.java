package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.MerchantProductCustomerLabelDao;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;


@Service("tMerchantProductCustomerLabelService")
public class MerchantProductCustomerLabelServiceImpl extends ServiceImpl<MerchantProductCustomerLabelDao, MerchantProductCustomerLabelEntity> implements MerchantProductCustomerLabelService {

    @Autowired
    MerchantService merchantService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String status = (String)params.get("status");
        String search = (String)params.get("search");
        String currency = (String)params.get("currency");
        Page<MerchantProductCustomerLabelEntity> page = this.selectPage(
                new Query<MerchantProductCustomerLabelEntity>(params).getPage(),
                new EntityWrapper<MerchantProductCustomerLabelEntity>()
                        .addFilterIfNeed(StringUtils.isNotBlank(status), "status in (" + status + ")")
                        .addFilterIfNeed(StringUtils.isNotBlank(currency) && currency.equals("通用"), "merchant_id = 0")
                        .addFilterIfNeed(StringUtils.isNotBlank(search),"(label_name like '%" + search + "%' " +
                                "or merchant_id in (select id from t_merchant where merchant_name like '%"+search+"%'))")
        );

        for(MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity : page.getRecords()){
            MerchantEntity merchantEntity = merchantService.selectById(merchantProductCustomerLabelEntity.getMerchantId());
            if (merchantEntity != null){
                merchantProductCustomerLabelEntity.setMerchantName(merchantEntity.getMerchantName());
            }
            if (merchantProductCustomerLabelEntity.getMerchantId().equals(0)){
                merchantProductCustomerLabelEntity.setMerchantName("商家通用标签");
            }
        }

        return new PageUtils(page);
    }

    @Override
    public List<MerchantProductCustomerLabelEntity> select() {
        List<MerchantProductCustomerLabelEntity> list =
                this.selectList(new EntityWrapper<MerchantProductCustomerLabelEntity>()
                        .addFilter("merchant_id = 0 and is_delete = 0")
                );
        return list;
    }

}
