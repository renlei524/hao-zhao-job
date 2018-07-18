package com.scxxwb.net.admin.modules.operation.controller;

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.scxxwb.net.admin.common.utils.*;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import com.scxxwb.net.admin.modules.operation.entity.MerchantCheckEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantCheckService;
import com.scxxwb.net.admin.modules.sys.controller.AbstractController;
import com.scxxwb.net.admin.modules.sys.entity.SysDeptEntity;
import com.scxxwb.net.admin.modules.sys.service.SysDeptService;
import com.scxxwb.net.admin.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.scxxwb.net.admin.modules.operation.entity.MerchantEntity;
import com.scxxwb.net.admin.modules.operation.service.MerchantService;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;


/**
 * 商户信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
@RestController
@RequestMapping("operation/merchant")
public class MerchantController extends AbstractController {
    @Value(value = "${scxxwb.ftp.url}")
    private String url;
    @Value(value = "${scxxwb.ftp.port}")
    private Integer port;
    @Value(value = "${scxxwb.ftp.user-name}")
    private String userName;
    @Value(value = "${scxxwb.ftp.password}")
    private String password;
    @Value(value = "${scxxwb.ftp.imagePath}")
    private String imagePath;
    @Value(value = "${scxxwb.nginx.imagePath}")
    private String imageNginxPath;
    @Value(value = "${scxxwb.payPath}")
    private String payPath;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantCheckService merchantCheckService;

    @Autowired
    private SysDeptService sysDeptService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:merchant:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = merchantService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:merchant:info")
    public R info(@PathVariable("id") Integer id){
        MerchantEntity merchant = merchantService.selectById(id);
        SysDeptEntity sysDeptEntity = sysDeptService.selectById(merchant.getAgentId());
        merchant.setAgentName(sysDeptEntity.getName());
        return R.ok().put("merchant", merchant).put("imageNginxPath", imageNginxPath);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:merchant:save")
    public R save(@RequestBody MerchantEntity merchant){
        merchant.setCreateTime(new Date());
        //添加创建人
        merchant.setSysUserId(ShiroUtils.getUserEntity().getUserId().intValue());
        // 默认密码：123456
        merchant.setLoginPwd(MD5Utils.MD5("123456"));
        // 创建待审核
        merchant.setStatus(1);
        merchant.setIsVoiceFunction(1);
        ValidatorUtils.validateEntity(merchant,AddGroup.class);
        Map map = new HashMap<String, Object>();
        map.put("loginUserName", merchant.getLoginUsername());
        map.put("merchantCode", merchant.getMerchantCode());
        map.put("merchantName", merchant.getMerchantName());
        Integer id = merchantService.getMerchantByLoginUserName(map);
        if( id != null) {
            return R.error("当前手机账号已存在！");
        }
        MerchantEntity merchantName = merchantService.getMerchantByMerchantName(map);
        if(merchantName != null) {
            return R.error("当前商户名称（店铺名称）已存在！");
        }
        MerchantEntity merchantCode = merchantService.getMerchantByCode(map);
        if( merchantCode != null) {
            return R.error("当前收款码已存在！");
        }
        merchantService.saveMerchant(merchant, map);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:merchant:update")
    public R update(@RequestBody MerchantEntity merchant){
        merchant.setUpdateTime(new Date());
        ValidatorUtils.validateEntity(merchant, UpdateGroup.class);
        Map map = new HashMap<String, Object>();
        map.put("merchantCode", merchant.getMerchantCode());
        map.put("merchantName", merchant.getMerchantName());
        MerchantEntity merchantName = merchantService.getMerchantByMerchantName(map);
        if(merchantName != null && !merchantName.getId().equals(merchant.getId()) && merchantName.getMerchantName().equals(merchant.getMerchantName())) {
            return R.error("当前商户名称（店铺名称）已存在！");
        }
        MerchantEntity merchantCode = merchantService.getMerchantByCode(map);
        if( merchantCode != null && !merchantCode.getId().equals(merchant.getId()) && merchantCode.getMerchantCode().equals(merchant.getMerchantCode())) {
            return R.error("当前收款码已存在！");
        }
        merchantService.updateMerchant(merchant);

        return R.ok();
    }

    /**
     * 禁用
     */
    @RequestMapping("/stop")
    @RequiresPermissions("operation:merchant:stop")
    public R stop(@RequestBody Integer[] ids){
        merchantService.stopMerchant(ids);
        //merchantCheckService.deletByMerchantIds(ids);

        return R.ok();
    }

    /**
     * 启用
     */
    @RequestMapping("/start")
    @RequiresPermissions("operation:merchant:start")
    public R start(@RequestBody Integer[] ids){
        merchantService.startMerchant(ids);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:merchant:delete")
    public R delete(@RequestBody Integer[] ids){
        merchantService.deleteBatchIds(Arrays.asList(ids));
        merchantCheckService.deletByMerchantIds(ids);

        return R.ok();
    }

    /**
     * image文件上传
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public R uploadApp(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //转为流
        InputStream inputStream = file.getInputStream();
        //获取上传的全名
        String originalFilename = file.getOriginalFilename();
        String imageName = DateUtils.format(new Date(), "yyyyMMddHHmmss-") + new Random().nextInt(1000000) + originalFilename;

        Boolean result = FTPUtils.storeFile(url, port, userName, password, imagePath, imageName, inputStream);
        if(!result) {
            return R.error();
        }
        //return R.ok().put("path", imageNginxPath + "/" + imageName);
        return R.ok().put("path", "/image/" + imageName).put("imageNginxPath", imageNginxPath);
    }

    /**
     * imageBase64文件上传
     */
    @RequestMapping(value = "/uploadImageBase64", method = RequestMethod.POST)
    @ResponseBody
    public R uploadImageBase64(String imageBase64) throws IOException {
        String imageStr = imageBase64.substring(imageBase64.indexOf(",") + 1);
        String imageName = DateUtils.format(new Date(), "yyyyMMddHHmmss-") + new Random().nextInt(1000000) + ".jpg";

        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = decoder.decodeBuffer(imageStr);
        for(int i=0;i<b.length;++i)
        {
            if(b[i]<0)
            {//调整异常数据
                b[i]+=256;
            }
        }
        //转为流
        InputStream inputStream = new ByteArrayInputStream(b);

        Boolean result = FTPUtils.storeFile(url, port, userName, password, imagePath, imageName, inputStream);
        if(!result) {
            return R.error();
        }
        //return R.ok().put("path", imageNginxPath + "/" + imageName);
        return R.ok().put("path", "/image/" + imageName).put("imageNginxPath", imageNginxPath);
    }

    /**
     * image文件删除
     */
    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    @ResponseBody
    public R deleteImage(String imageName) throws IOException {
        Boolean result = FTPUtils.deleteFile(url, port, userName, password, imagePath, imageName);
        if(!result) {
            return R.error();
        }
        return R.ok();
    }

    /**
     * 展示收款码
     */
    @RequestMapping(value = "/showMerchantCodeImage", method = RequestMethod.POST)
    @ResponseBody
    public R showMerchantCodeImage(String merchantCode) throws IOException {
        if(merchantCode.isEmpty()) {
            return R.error("收款码不能为空！");
        }
        InputStream is = ZXingCodeUtils.drawLogoCodeForBuffer(null,payPath + merchantCode,"");
        byte[] data = new byte[is.available()];
        is.read(data);
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return R.ok().put("codeBase64", encoder.encode(data));
    }
}
