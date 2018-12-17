package leiren.haozhaojob.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.sys.entity.AppEntity;

import java.util.Map;

public interface AppService extends IService<AppEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
