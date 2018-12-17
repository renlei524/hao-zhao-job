package leiren.haozhaojob.modules.operation.dao;

import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 用户基础信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
public interface WbUserDao extends BaseMapper<WbUserEntity> {
	
    List<Integer> selectUserIdByUserNameOrMobile(String search);

    WbUserEntity selectUserNameAndMobileByUserId(Integer id);
}
