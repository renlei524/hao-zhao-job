package leiren.haozhaojob.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.sys.entity.SysNoticeEntity;

import java.util.Map;

/**
 * 
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-21 11:52:09
 */
public interface SysNoticeService extends IService<SysNoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

