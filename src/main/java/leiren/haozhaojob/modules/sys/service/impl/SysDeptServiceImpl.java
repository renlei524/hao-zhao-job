package leiren.haozhaojob.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.modules.sys.dao.SysDeptDao;
import leiren.haozhaojob.modules.sys.entity.SysDeptEntity;
import leiren.haozhaojob.modules.sys.entity.SysUserEntity;
import leiren.haozhaojob.modules.sys.service.SysDeptService;
import leiren.haozhaojob.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	protected KafkaProducer kafkaProducer;
	@Autowired
	protected SysDeptService sysDeptService;

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAndForbidBySysDeptEntity(SysDeptEntity dept) {
		//判断是否禁用此公司下面的所有子公司以及员工
		if (dept.getStatus() == 0) {
			//找出此公司下的所有子公司
			Map map = new HashMap<String, Object>();
			ArrayList<Integer> integers = new ArrayList<>();
			List<Integer> arr = integers;
			arr.add(dept.getDeptId());
			List<SysDeptEntity> deptList = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				map.put("parent_id", arr.get(i));
				deptList = sysDeptService.selectByMap(map);
				if (deptList != null) {
					for (SysDeptEntity sysDeptEntity : deptList) {
						arr.add(sysDeptEntity.getDeptId());
					}
				}
				map.clear();
			}
			//禁用所有子公司（包括本公司）
			sysDeptService.updateDeptByParentId(arr);
			//禁用所有子公司下面的员工（包括本公司）
			sysUserService.updateUserByDeptId(arr);
		}
		sysDeptService.updateById(dept);
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
