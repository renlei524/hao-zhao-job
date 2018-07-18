package com.scxxwb.net.admin.modules.statistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantDayIncomeEntity;
import com.scxxwb.net.admin.modules.statistics.entity.MerchantIncomeDetailEntity;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户收入详情
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.06.21
 */
public interface MerchantIncomeDetailDao extends BaseMapper<MerchantIncomeDetailEntity> {

    MerchantIncomeDetailEntity getMerchantIncomeDetailBymerchantId(Map<String, Object> params);
}
