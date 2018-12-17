package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.modules.operation.dao.GoodsDao;
import leiren.haozhaojob.modules.operation.entity.GoodsEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import leiren.haozhaojob.modules.operation.entity.MerchantProductCustomerLabelEntity;
import leiren.haozhaojob.modules.operation.service.GoodsService;
import leiren.haozhaojob.modules.operation.service.MerchantProductCustomerLabelService;
import leiren.haozhaojob.modules.operation.service.MerchantService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("tGoodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {
    @Value(value = "${scxxwb.nginx.goodsPath}")
    private String goodsNginxPath;

    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantProductCustomerLabelService merchantProductCustomerLabelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String search = (String)params.get("search");
        String status = (String)params.get("status");
        Page<GoodsEntity> page = this.selectPage(
                new Query<GoodsEntity>(params).getPage(),
                new EntityWrapper<GoodsEntity>()
                        .addFilterIfNeed(StringUtils.isNotBlank(status) && !status.equals("0"),"status in ("+status+")")
                        .addFilterIfNeed(StringUtils.isNotBlank(search),"(goods_name like '%" + search + "%' " +
                                "or merchant_id in (select id from t_merchant where merchant_name like '%"+search+"%'))")
        );
        for(GoodsEntity goodsEntity : page.getRecords()){
            goodsEntity.setOriginalPrice(goodsEntity.getOriginalPrice().divide(new BigDecimal("100")));
            goodsEntity.setMarketPrice(goodsEntity.getMarketPrice().divide(new BigDecimal("100")));
            goodsEntity.setDiscountPrice(goodsEntity.getDiscountPrice().divide(new BigDecimal("100")));
            goodsEntity.setPromotionalPrice(goodsEntity.getPromotionalPrice().divide(new BigDecimal("100")));
            goodsEntity.setPictures(goodsNginxPath + goodsEntity.getPictures());
            MerchantEntity merchantEntity = merchantService.selectById(goodsEntity.getMerchantId());
            if (merchantEntity != null){
                goodsEntity.setMerchantName(merchantEntity.getMerchantName());
            }
            MerchantProductCustomerLabelEntity merchantProductCustomerLabelEntity = merchantProductCustomerLabelService.selectById(goodsEntity.getLabelId());
            if (merchantProductCustomerLabelEntity != null){
                goodsEntity.setLabelName(merchantProductCustomerLabelEntity.getLabelName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    public Integer totalByMerchantId(Integer merchantId) {
        return baseMapper.totalByMerchantId(merchantId);
    }

    @Override
    public Integer insertGoods(GoodsEntity goodsEntity) {
        return baseMapper.insertGoods(goodsEntity);
    }

}
