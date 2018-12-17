package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.DiandiankeJinJianEntity;

import java.util.Map;

public interface DiandiankeJinJianService extends IService<DiandiankeJinJianEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
