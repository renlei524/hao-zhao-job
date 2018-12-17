package leiren.haozhaojob.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import leiren.haozhaojob.modules.sys.entity.WbAreaEntity;

/**
 * 地区信息
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
public interface WbAreaDao extends BaseMapper<WbAreaEntity> {

    public WbAreaEntity selectByAreaCode(Integer areaCode);

}
