package com.scxxwb.net.admin.modules.operation.controller;

import java.util.*;

import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scxxwb.net.admin.modules.operation.entity.TWbUserEntity;
import com.scxxwb.net.admin.modules.operation.service.TWbUserService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;



/**
 * 用户基础信息表
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@RestController
@RequestMapping("operation/twbuser")
public class TWbUserController extends AbstractController {
    @Autowired
    private TWbUserService tWbUserService;

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
    public List<TWbUserEntity> select(){
        List<TWbUserEntity> userList = tWbUserService.queryList(new HashMap<String, Object>());

        return userList;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:twbuser:info")
    public R info(@PathVariable("id") String id){
        TWbUserEntity tWbUser = tWbUserService.selectById(id);

        return R.ok().put("tWbUser", tWbUser);
    }

    /**
     * 信息
     */
    private List<TWbUserEntity> infoByMobile(String mobile){
        Map map = new HashMap<String, Object>();
        map.put("mobile",mobile);
        List<TWbUserEntity> list = tWbUserService.selectByMap(map);
        return list;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:twbuser:save")
    public R save(@RequestBody TWbUserEntity tWbUser){
        ValidatorUtils.validateEntity(tWbUser,AddGroup.class);
        //判断此电话号是否存在
        List<TWbUserEntity> tWbUserEntityList = infoByMobile(tWbUser.getMobile());
        if (tWbUserEntityList.size() != 0){
            return R.error(1,"用户手机号已存在，请重新输入");
        }
        tWbUserService.saveUser(tWbUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:twbuser:update")
    public R update(@RequestBody TWbUserEntity tWbUser){
        ValidatorUtils.validateEntity(tWbUser,UpdateGroup.class);
        //判断此电话号是否存在
        List<TWbUserEntity> tWbUserEntityList = infoByMobile(tWbUser.getMobile());
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
    @RequestMapping("/delete")
    @RequiresPermissions("operation:twbuser:delete")
    public R delete(@RequestBody String[] ids){
        tWbUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
