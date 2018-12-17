package leiren.haozhaojob.modules.sys.controller;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.utils.DateUtils;
import leiren.haozhaojob.common.utils.FTPUtils;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.sys.entity.AppEntity;
import leiren.haozhaojob.modules.sys.service.AppService;
import leiren.haozhaojob.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * app版本管理
 *
 * @author liyun
 * @email 25637208209@qq.com
 * @date 2018-06-26 17:01:55
 */
@RestController
@RequestMapping("sys/app")
public class AppController extends AbstractController {
    @Value(value = "${scxxwb.ftp.url}")
    private String url;
    @Value(value = "${scxxwb.ftp.port}")
    private Integer port;
    @Value(value = "${scxxwb.ftp.user-name}")
    private String userName;
    @Value(value = "${scxxwb.ftp.password}")
    private String password;
    @Value(value = "${scxxwb.ftp.appPath}")
    private String appPath;
    @Value(value = "${scxxwb.nginx.appPath}")
    private String appNginxPath;

    @Autowired
    private AppService tSysAppService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:app:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tSysAppService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:app:info")
    public R info(@PathVariable("id") Integer id){
        AppEntity sysApp = tSysAppService.selectById(id);

        return R.ok().put("sysApp", sysApp);
    }

    /**
     * app文件上传
     */
    @RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
    @ResponseBody
    @SysLog("app文件上传")
    public R uploadApp(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //转为流
        InputStream inputStream = file.getInputStream();
        //获取上传的全名
        String originalFilename = file.getOriginalFilename();
        String fileName = DateUtils.format(new Date(), "yyyyMMddHHmmss-") + new Random().nextInt(1000000) + originalFilename;

        Boolean result = FTPUtils.storeFile(url, port, userName, password, appPath, fileName, inputStream);
        if(!result) {
            return R.error();
        }
        //return R.ok().put("path", appNginxPath + "/" + fileName);
        return R.ok().put("path", "/appPkg/" + fileName);
    }

    /**
     * app文件删除
     */
    @RequestMapping(value = "/deleteApp", method = RequestMethod.POST)
    @ResponseBody
    @SysLog("app文件删除")
    public R deleteApp(String fileName) throws IOException {
        Boolean result = FTPUtils.deleteFile(url, port, userName, password, appPath, fileName);
        if(!result) {
            return R.error();
        }
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:app:save")
    @SysLog("保存app版本")
    public R save(@RequestBody AppEntity tSysApp){
        tSysApp.setCreateTime(new Date());
        tSysApp.setUserId(ShiroUtils.getUserEntity().getUserId().intValue());
        ValidatorUtils.validateEntity(tSysApp,AddGroup.class);
        tSysAppService.insert(tSysApp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:app:update")
    @SysLog("修改app版本")
    public R update(@RequestBody AppEntity tSysApp){
        ValidatorUtils.validateEntity(tSysApp,UpdateGroup.class);
        tSysAppService.updateAllColumnById(tSysApp);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:app:delete")
    @SysLog("删除app版本")
    public R delete(@RequestBody Integer[] ids){
        tSysAppService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
