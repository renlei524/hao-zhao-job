package leiren.haozhaojob.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.utils.DateUtils;
import leiren.haozhaojob.common.utils.FTPUtils;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.sys.entity.SysNoticeEntity;
import leiren.haozhaojob.modules.sys.service.SysNoticeService;
import leiren.haozhaojob.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 通知管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("sys/notice")
public class SysNoticeController extends AbstractController {
    @Value(value = "${scxxwb.ftp.url}")
    private String url;
    @Value(value = "${scxxwb.ftp.port}")
    private Integer port;
    @Value(value = "${scxxwb.ftp.user-name}")
    private String userName;
    @Value(value = "${scxxwb.ftp.password}")
    private String password;
    @Value(value = "${scxxwb.ftp.noticePath}")
    private String noticePath;
    @Value(value = "${scxxwb.nginx.noticePath}")
    private String noticNginxPath;

    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:notice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysNoticeService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 置顶数量
     */
    @RequestMapping("/isTopNum")
    @RequiresPermissions("sys:notice:isTopNum")
    public R isTopNum(){
        Integer isTopNum = sysNoticeService.selectCount(new EntityWrapper<SysNoticeEntity>().eq("is_top", 1));
        return R.ok().put("isTopNum", isTopNum);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:notice:info")
    public R info(@PathVariable("id") Integer id){
        SysNoticeEntity sysNotice = sysNoticeService.selectById(id);

        return R.ok().put("sysNotice", sysNotice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:notice:save")
    @SysLog("添加公告")
    public R save(@RequestBody SysNoticeEntity sysNotice){
        sysNotice.setCreateTime(new Date());
        sysNotice.setUserId(ShiroUtils.getUserEntity().getUserId().intValue());
        ValidatorUtils.validateEntity(sysNotice, AddGroup.class);
        sysNoticeService.insert(sysNotice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:notice:update")
    @SysLog("修改公告")
    public R update(@RequestBody SysNoticeEntity sysNotice){
        sysNotice.setUpdateTime(new Date());
        ValidatorUtils.validateEntity(sysNotice, UpdateGroup.class);
        sysNoticeService.updateAllColumnById(sysNotice);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:notice:delete")
    public R delete(@RequestBody Integer[] ids){
        sysNoticeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * image文件上传
     */
    //@SysLog("image文件上传")
    @CrossOrigin
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadApp(@RequestParam("upfile") MultipartFile file) throws IOException {
        //转为流
        InputStream inputStream = file.getInputStream();
        //获取上传的全名
        String originalFilename = file.getOriginalFilename();
        String imageName = DateUtils.format(new Date(), "yyyyMMddHHmmss-") + new Random().nextInt(1000000) + originalFilename;

        Boolean result = FTPUtils.storeFile(url, port, userName, password, noticePath, imageName, inputStream);
        if(!result) {
            return JSON.toJSONString(R.error());
        }
        return JSON.toJSONString(R.ok().put("url", noticNginxPath + "/image/" + imageName).put("state", "SUCCESS"));
    }
}
