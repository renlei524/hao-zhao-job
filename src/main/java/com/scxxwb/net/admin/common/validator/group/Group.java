package com.scxxwb.net.admin.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
