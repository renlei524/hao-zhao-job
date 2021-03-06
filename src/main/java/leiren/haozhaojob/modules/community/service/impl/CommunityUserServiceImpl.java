package leiren.haozhaojob.modules.community.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import leiren.haozhaojob.common.annotation.DataFilter;
import leiren.haozhaojob.common.kafka.KafkaProducer;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.Query;
import leiren.haozhaojob.modules.community.dao.CommunityUserDao;
import leiren.haozhaojob.modules.community.entity.CommunitySysDeptEntity;
import leiren.haozhaojob.modules.community.entity.CommunityUserEntity;
import leiren.haozhaojob.modules.community.service.CommunitySysDeptService;
import leiren.haozhaojob.modules.community.service.CommunityUserRoleService;
import leiren.haozhaojob.modules.community.service.CommunityUserService;
import leiren.haozhaojob.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统用户
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Service("CommunityUserService")
public class CommunityUserServiceImpl extends ServiceImpl<CommunityUserDao, CommunityUserEntity> implements CommunityUserService {
	@Autowired
	private CommunityUserRoleService communityUserRoleService;
	@Autowired
	private CommunitySysDeptService communitySysDeptService;
	@Autowired
	protected KafkaProducer kafkaProducer;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		//数据权限管理
		String deptIds = null;
		if(params.get(Constant.SQL_FILTER) != null){
			String num =  params.get(Constant.SQL_FILTER).toString();
			num = num.substring(13, num.length() - 2);
			String[] strs = num.split(",");
			List<String> list = Arrays.asList(strs);
			//所属分公司字段过滤
			deptIds = StringUtils.join(list, ',');
		}
		//获取查询参数
		String queryName = String.valueOf(params.get("userName")).trim();
		boolean flag = StringUtils.isNotBlank((String)params.get("userName"));
		String deptIdFromSysUser = null;
		if(queryName != "null" && !queryName.isEmpty()) {
			List<String> deptList = communitySysDeptService.selectDeptIdByName(queryName);
			if(deptList.size() != 0){
				deptIdFromSysUser = StringUtils.join(deptList, ",");
			}
		}
        //初始化对象
		Page<CommunityUserEntity> page = this.selectPage(
				new Query<CommunityUserEntity>(params).getPage(),
				new EntityWrapper<CommunityUserEntity>()
						.addFilterIfNeed(deptIdFromSysUser != null, "dept_id IN(" + deptIdFromSysUser + ")" )
						.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "dept_id IN (SELECT dept_id FROM t_community_sys_dept WHERE sys_dept_id IN(" + deptIds + "))")
						.andNew(flag, "user_name LIKE '%" + queryName + "%'")
						.or(flag, "mobile LIKE '%" + queryName + "%'")
						.or(flag, "real_name LIKE '%" + queryName + "%'")
		);
		List<CommunityUserEntity> list = page.getRecords();
		for(CommunityUserEntity communityUserEntity : list){
			CommunitySysDeptEntity sysDeptEntity = communitySysDeptService.selectById(communityUserEntity.getDeptId());
			if(sysDeptEntity != null) {
				communityUserEntity.setDeptName(sysDeptEntity.getName());
			}
		}
		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(CommunityUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.insert(user);
		
		//保存用户与角色关系
		communityUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(CommunityUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		this.updateById(user);
		
		//保存用户与角色关系
		communityUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
        CommunityUserEntity userEntity = new CommunityUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<CommunityUserEntity>().eq("user_id", userId).eq("password", password));
    }
}
