package com.scxxwb.net.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.scxxwb.net.admin.modules.sys.entity.TWbAreaEntity;

/**
 * 地区信息
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
public interface TWbAreaDao extends BaseMapper<TWbAreaEntity> {

    public TWbAreaEntity selectByAreaCode(Integer areaCode);

}
