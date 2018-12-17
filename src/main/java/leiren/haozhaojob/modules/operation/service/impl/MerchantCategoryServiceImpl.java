package leiren.haozhaojob.modules.operation.service.impl;

import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import leiren.haozhaojob.modules.operation.dao.MerchantCategoryDao;
import leiren.haozhaojob.modules.operation.entity.MerchantCategoryEntity;
import leiren.haozhaojob.modules.operation.service.MerchantCategoryService;


@Service("merchantCategoryService")
public class MerchantCategoryServiceImpl extends ServiceImpl<MerchantCategoryDao, MerchantCategoryEntity> implements MerchantCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MerchantCategoryEntity> page = this.selectPage(
                new Query<MerchantCategoryEntity>(params).getPage(),
                new EntityWrapper<MerchantCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MerchantCategoryEntity> queryList(Map<String, Object> map) {
        List<MerchantCategoryEntity> MerchantCategoryList =
                this.selectList(new EntityWrapper<MerchantCategoryEntity>()
                        .addFilterIfNeed(map.get(Constant.SQL_FILTER) != null, (String)map.get(Constant.SQL_FILTER)));
        for (MerchantCategoryEntity merchantCategoryEntity:MerchantCategoryList){
            MerchantCategoryEntity merchantCategory =this.selectById(merchantCategoryEntity.getParentId());
            if (merchantCategory !=null){
                merchantCategoryEntity.setParentName(merchantCategory.getName());
            }
        }
        return MerchantCategoryList;
    }

    @Override
    public List<Long> queryMerchantCategoryIdList(Long parentId) {
        return baseMapper.queryMerchantCategoryIdList(parentId);
    }

}
