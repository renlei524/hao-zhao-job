package com.scxxwb.net.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.sys.dao.SysUserDao;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.service.SysUserRoleService;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	public List<Integer> queryAllMenuId(Integer userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		//获取查询参数
		String queryName = String.valueOf(params.get("userName")).trim();
		boolean flag = StringUtils.isNotBlank((String)params.get("userName"));
		//根据模糊查询公司名称
		List<SysDeptEntity> deptList = new ArrayList<>();
		String num = "";
		if(queryName != "null" && !queryName.isEmpty()){
			deptList =
					sysDeptService.selectList(new EntityWrapper<SysDeptEntity>().like("name",queryName));
			if (deptList.size() != 0){
				for (SysDeptEntity sysDeptEntity : deptList){
					num = num + sysDeptEntity.getDeptId().toString()+",";
				}
				num = num.substring(0,num.length() - 1);
			}
		}

		//查询用户信息
		Page<SysUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.andNew(flag, "real_name LIKE '%" + queryName + "%'")
				.or(flag, "mobile LIKE '%" + queryName + "%'")
				.or(flag && deptList.size() != 0, "dept_id IN (" + num + ")")
				.or(flag, "user_name LIKE '%" + queryName + "%'")
		);

		for(SysUserEntity sysUserEntity : page.getRecords()){
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
			if (sysDeptEntity != null){
				sysUserEntity.setDeptName(sysDeptEntity.getName());
			}
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.insert(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Integer userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

	@Override
	public int updateUserByDeptId(List<Integer> arr) {
		return baseMapper.updateUserByDeptId(arr);
	}

}
