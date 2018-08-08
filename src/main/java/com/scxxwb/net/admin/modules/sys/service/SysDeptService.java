package com.scxxwb.net.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptEntity> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Integer> queryDetpIdList(Integer parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Integer> getSubDeptIdList(Integer deptId);

	/**
	 * 根据id修改信息
	 * @param arr
	 * @return
	 */
	int updateDeptByParentId(List<Integer> arr);

	void updateAndForbidBySysDeptEntity(SysDeptEntity sysDeptEntity);
}
