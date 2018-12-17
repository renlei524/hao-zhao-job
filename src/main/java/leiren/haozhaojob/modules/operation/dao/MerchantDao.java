package leiren.haozhaojob.modules.operation.dao;

import leiren.haozhaojob.modules.operation.entity.MerchantEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 商户信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
public interface MerchantDao extends BaseMapper<MerchantEntity> {

    List<Integer> getMerchant(List<String> ages);

    Integer getMerchantByLoginUserName(Map<String, Object> params);

    MerchantEntity getMerchantByCode(Map<String, Object> params);

    MerchantEntity getMerchantByMerchantName(Map<String, Object> params);

    int getMerchantByAreaTotal (Map<String, Object> params);

    List<MerchantEntity> getMerchantByArea(Map<String, Object> params);

    Boolean updateStatus(Map<String, Object> map);

    Boolean updateStatusAndRemarkAndUpdateTime(Map<String, Object> map);

    List<Integer> selectMerchantIdByMerchantName(String params);

    /**
     * 根据商户id查询商户名称、简单地址、业务员id
     * @param id 商户id
     * @return 商户实体
     */
    MerchantEntity selectMerchantNameSAdressAndSManById(Integer id);

    /**
     * 根据商户名称模糊查询商户id集合
     * @param merchantName 查询条件（名称）
     * @return 商户id集合
     */
    List<Integer> selectMerchantIdWhereNameLikeSearch(String merchantName);

    /**
     * 根据商户creatorId 查询商户姓名
     * @param creatorId
     * @return 商户名称
     */
    String selectMerchantNameByCreatorId(Integer creatorId);

    /**
     * 按商户名称和电话模糊查询商户id集合
     * @param merchantName 商户名称
     * @param telphone 电话
     * @return 商户id集合
     */
    List<Integer> selectMerchantIdsByNameAndMobile(String merchantName, String telphone);

    /**
     * 根据商户id查询商户名称和电话
     * @param merchantId 商户id
     * @return 商户实体
     */
    MerchantEntity getMerchantNameAndTelephoneByMerchantId(Integer merchantId);
}
