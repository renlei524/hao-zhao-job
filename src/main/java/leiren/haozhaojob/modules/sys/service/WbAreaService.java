package leiren.haozhaojob.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.sys.entity.WbAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 地区信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
public interface WbAreaService extends IService<WbAreaEntity> {


    List<WbAreaEntity> queryList(Map<String, Object> map);

    PageUtils queryPage(Map<String, Object> params);

    List<WbAreaEntity> infoList(Map<String, Object> params, String parentId);

    WbAreaEntity selectByAreaCode(Integer areaCode);

    void updateAreaByTWBArea(Map<String, Object> params, WbAreaEntity tWbAreaEntity);

}

