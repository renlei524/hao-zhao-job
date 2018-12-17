package leiren.haozhaojob.modules.community.controller;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.exception.RRException;
import leiren.haozhaojob.common.utils.Constant;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.modules.community.entity.CommunityMenuEntity;
import leiren.haozhaojob.modules.community.service.CommunityMenuService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统菜单
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("/community/menu")
public class CommunityMenuController extends AbstractController {
	@Autowired
	private CommunityMenuService communityMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public R nav(){
		List<CommunityMenuEntity> menuList = communityMenuService.getUserMenuList(Long.valueOf(getUserId()));
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("community:menu:list")
	public List<CommunityMenuEntity> list(){
		List<CommunityMenuEntity> menuList = communityMenuService.selectList(null);
		for(CommunityMenuEntity communityMenuEntity : menuList){
			CommunityMenuEntity parentMenuEntity = communityMenuService.selectById(communityMenuEntity.getParentId());
			if(parentMenuEntity != null){
				communityMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("community:menu:select")
	public R select(){
		//查询列表数据
		List<CommunityMenuEntity> menuList = communityMenuService.queryNotButtonList();
		
		//添加顶级菜单
		CommunityMenuEntity root = new CommunityMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("community:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		CommunityMenuEntity menu = communityMenuService.selectById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存社区菜单")
	@RequestMapping("/save")
	@RequiresPermissions("community:menu:save")
	public R save(@RequestBody CommunityMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		communityMenuService.insert(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改社区菜单")
	@RequestMapping("/update")
	@RequiresPermissions("community:menu:update")
	public R update(@RequestBody CommunityMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		communityMenuService.updateById(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除社区菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("community:menu:delete")
	public R delete(long menuId){
		if(menuId <= 31){
			return R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<CommunityMenuEntity> menuList = communityMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		communityMenuService.delete(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(CommunityMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			CommunityMenuEntity parentMenu = communityMenuService.selectById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
