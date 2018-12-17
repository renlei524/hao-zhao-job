package leiren.haozhaojob.modules.operation.controller;

import java.util.*;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.operation.entity.WbUserEntity;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import leiren.haozhaojob.modules.operation.service.WbUserService;


/**
 * 用户基础信息表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@RestController
@RequestMapping("operation/twbuser")
public class WbUserController extends AbstractController {
    @Autowired
    private WbUserService tWbUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:twbuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tWbUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 选择用户(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("operation:twbuser:select")
    public List<WbUserEntity> select(){
        List<WbUserEntity> userList = tWbUserService.queryList(new HashMap<String, Object>());

        return userList;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:twbuser:info")
    public R info(@PathVariable("id") String id){
        WbUserEntity tWbUser = tWbUserService.selectById(id);

        return R.ok().put("tWbUser", tWbUser);
    }

    /**
     * 信息
     */
    private List<WbUserEntity> infoByMobile(String mobile){
        Map map = new HashMap<String, Object>();
        map.put("mobile",mobile);
        List<WbUserEntity> list = tWbUserService.selectByMap(map);
        return list;
    }

    /**
     * 保存
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("operation:twbuser:save")
    public R save(@RequestBody WbUserEntity tWbUser){
        ValidatorUtils.validateEntity(tWbUser,AddGroup.class);
        //判断此电话号是否存在
        List<WbUserEntity> tWbUserEntityList = infoByMobile(tWbUser.getMobile());
        if (tWbUserEntityList.size() != 0){
            return R.error(1,"用户手机号已存在，请重新输入");
        }
        tWbUserService.saveUser(tWbUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("operation:twbuser:update")
    public R update(@RequestBody WbUserEntity tWbUser){
        ValidatorUtils.validateEntity(tWbUser,UpdateGroup.class);
        //判断此电话号是否存在
        List<WbUserEntity> tWbUserEntityList = infoByMobile(tWbUser.getMobile());
        if (tWbUserEntityList.size() != 0){
            if (!tWbUserEntityList.get(0).getId().equals(tWbUser.getId())){
                return R.error(1,"用户手机号已存在，请重新输入");
            }
        }
        tWbUser.setUpdateTime(new Date());
        tWbUserService.updateAllColumnById(tWbUser);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("operation:twbuser:delete")
    public R delete(@RequestBody String[] ids){
        tWbUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


}
