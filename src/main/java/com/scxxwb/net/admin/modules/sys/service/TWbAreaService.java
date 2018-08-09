package com.scxxwb.net.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.modules.sys.entity.TWbAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 地区信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
public interface TWbAreaService extends IService<TWbAreaEntity> {


    List<TWbAreaEntity> queryList(Map<String, Object> map);

    PageUtils queryPage(Map<String, Object> params);

    List<TWbAreaEntity> infoList(Map<String, Object> params, String parentId);

    TWbAreaEntity selectByAreaCode(Integer areaCode);

    void updateAreaByTWBArea(Map<String, Object> params, TWbAreaEntity tWbAreaEntity);

}

