package leiren.haozhaojob.modules.operation.service;

import com.baomidou.mybatisplus.service.IService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户基础信息表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
public interface WbUserService extends IService<WbUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<WbUserEntity> queryList(Map<String, Object> map);

    Boolean saveUser(WbUserEntity tWbUserEntity);

    List<Integer> selectUserIdByUserNameOrMobile(String search);

    WbUserEntity selectUserNameAndMobileByUserId(Integer id);
}

