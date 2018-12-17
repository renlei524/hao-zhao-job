package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.modules.operation.dao.GoodsSkuDao;
import leiren.haozhaojob.modules.operation.entity.GoodsSkuEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;
import leiren.haozhaojob.modules.operation.service.GoodsSkuService;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("tSkuGoodsService")
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuDao, GoodsSkuEntity> implements GoodsSkuService {

    @Autowired
    MerchantProductCustomerLabelService merchantProductCustomerLabelService;

    @Autowired
    SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String search = (String)params.get("search");
        String number = (String)params.get("number");
        String status = (String)params.get("status");
        Page<GoodsSkuEntity> page = this.selectPage(
                new Query<GoodsSkuEntity>(params).getPage(),
                new EntityWrapper<GoodsSkuEntity>()
                .addFilterIfNeed(StringUtils.isNotBlank(number),"code like '%" + number + "%'")
                .addFilterIfNeed(StringUtils.isNotBlank(status) && !status.equals("0"),"status = " + status)
                .addFilterIfNeed(StringUtils.isNotBlank(search),"(goods_name like '%" + search + "%'" +
                        " or label_id in (select label_id from t_merchant_product_customer_label where label_name like '%" + search + "%')" +
                        " or user_id in (select user_id from t_sys_user where real_name like '%" + search + "%'))")
        );

        for (GoodsSkuEntity goodsSkuEntity : page.getRecords()){
            MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity = merchantProductCustomerLabelService.selectById(goodsSkuEntity.getLabelId());
            if (merchantProductCustomerLabelEntity != null){
                goodsSkuEntity.setLabelName(merchantProductCustomerLabelEntity.getLabelName());
            }
            SysUserEntity sysUserEntity = sysUserService.selectById(goodsSkuEntity.getUserId());
            if (sysUserEntity != null){
                goodsSkuEntity.setUserName(sysUserEntity.getRealName());
            }
        }
        return new PageUtils(page);
    }

}
