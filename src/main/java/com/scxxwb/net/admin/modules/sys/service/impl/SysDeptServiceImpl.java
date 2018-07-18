package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.modules.sys.dao.SysDeptDao;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	@DataFilter(subDept = true, user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> params){
		List<SysDeptEntity> deptList =
			this.selectList(new EntityWrapper<SysDeptEntity>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)) );

		for(SysDeptEntity sysDeptEntity : deptList){
			SysDeptEntity parentDeptEntity =  this.selectById(sysDeptEntity.getParentId());
			if(parentDeptEntity != null){
				sysDeptEntity.setParentName(parentDeptEntity.getName());
			}

			SysUserEntity sysUserEntity = sysUserService.selectById(sysDeptEntity.getUserId());
			if(sysUserEntity != null){
				sysDeptEntity.setUserName(sysUserEntity.getRealName());
			}
		}

		return deptList;
	}

	@Override
	public List<Integer> queryDetpIdList(Integer parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Integer> getSubDeptIdList(Integer deptId){
		//部门及子部门ID列表
		List<Integer> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Integer> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	@Override
	public int updateDeptByParentId(List<Integer> arr) {
		return baseMapper.updateDeptByParentId(arr);
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Integer> subIdList, List<Integer> deptIdList){
		for(Integer deptId : subIdList){
			List<Integer> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
