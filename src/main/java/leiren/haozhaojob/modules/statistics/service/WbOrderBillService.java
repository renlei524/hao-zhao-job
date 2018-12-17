package leiren.haozhaojob.modules.statistics.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.statistics.entity.WbOrderBillEntity;

import java.util.Map;

/**
 * 结算单据表0
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-17 14:11:41
 */
public interface WbOrderBillService extends IService<WbOrderBillEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

