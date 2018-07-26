package com.scxxwb.net.admin.modules.operation.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Wrapper;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.JsonObject;
import com.scxxwb.net.admin.common.utils.DateUtils;
import com.scxxwb.net.admin.common.utils.FTPUtils;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.scxxwb.net.admin.modules.operation.entity.TVyicooJinjianEntity;
import com.scxxwb.net.admin.modules.operation.service.TVyicooJinjianService;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.R;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;

import static com.qcloud.cos.http.RequestHeaderValue.Method.POST;


/**
 *  微易客进件
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
@Api(value = "operation/tvyicoojinjian", tags = "微易客进件")
@RestController
@RequestMapping("operation/tvyicoojinjian")
public class TVyicooJinjianController {
    @Value(value = "${scxxwb.ftp.url}")
    private String url;
    @Value(value = "${scxxwb.ftp.port}")
    private Integer port;
    @Value(value = "${scxxwb.ftp.user-name}")
    private String userName;
    @Value(value = "${scxxwb.ftp.password}")
    private String password;
    @Value(value = "${scxxwb.ftp.vyicooPath}")
    private String vyicooPath;
    @Value(value = "${scxxwb.nginx.imagePath}")
    private String imageNginxPath;
    @Value(value = "${scxxwb.payPath}")
    private String payPath;

    @Resource
    RestTemplate restTemplate;

    @Autowired
    private TVyicooJinjianService tVyicooJinjianService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:tvyicoojinjian:list")
    @ApiOperation(value = "列表", httpMethod = POST)
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tVyicooJinjianService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{type}")
    @RequiresPermissions("operation:tvyicoojinjian:info")
    @ApiOperation(value = "根据type查询信息", httpMethod = POST)
    public R info(
//            @PathVariable("type") String type
            @ApiParam(value = "type", required = true) @PathVariable String type
        ){
        TVyicooJinjianEntity tVyicooJinjian = tVyicooJinjianService.selectById(type);

        return R.ok().put("tVyicooJinjian", tVyicooJinjian);
    }

    /**
     * 审核回调
     */
    @RequestMapping("/callback")
    @CrossOrigin
    @ApiOperation(value = "审核回调", httpMethod = POST)
    public R check(@RequestParam Map<String, Object> params){
        tVyicooJinjianService.updateStatus(params);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:tvyicoojinjian:save")
    @ApiOperation(value = "新增微易客进件", httpMethod = POST)
    public R save(@RequestBody  @ApiParam( name = "进件对象", value = "传入json格式", required = true) TVyicooJinjianEntity tVyicooJinjian){
        String payment =
                "{" +
                    "'T1': 38, " +
                    "'weixin': {" +
                                "'mp': {" +
                                    "'appid': 'wx4a2530ee65d5f186'," +
                                    "'pay_dir': 'http://wx.scxxwb.com/Pay/Trade/'" +
                                "}" +
                            "}" +
                "}";
        tVyicooJinjian.setPayment(payment);
        ValidatorUtils.validateEntity(tVyicooJinjian);
//        Map paramMap = JSONObject.parseObject(JSONObject.toJSONString(tVyicooJinjian));

//        ResponseEntity<String> mapResponseEntity = restTemplate.postForEntity("https://pay.vyicoo.com/v3/mch/create", basicParam(paramMap), String.class);
//        Map map = JSONObject.parseObject(mapResponseEntity.getBody());
//        Integer status = Integer.parseInt(map.get("status").toString());
//        if(status != 0) {
//            return R.error("进件资料上传失败！");
//        }
//        tVyicooJinjian.setMchId(JSONObject.parseObject(map.get("data").toString()).get("mch_id").toString());
        tVyicooJinjian.setVerifyStatus(1); // 创建审核中
        tVyicooJinjianService.insert(tVyicooJinjian);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:tvyicoojinjian:update")
    @ApiOperation(value = "修改微易客进件", httpMethod = POST)
    public R update(@RequestBody @ApiParam( name = "进件对象", value = "传入json格式", required = true)TVyicooJinjianEntity tVyicooJinjian){
        ValidatorUtils.validateEntity(tVyicooJinjian);
        Map paramMap = JSONObject.parseObject(JSONObject.toJSONString(tVyicooJinjian));

        ResponseEntity<String> mapResponseEntity = restTemplate.postForEntity("https://pay.vyicoo.com/v3/mch/update", basicParam(paramMap), String.class);
        Map map = JSONObject.parseObject(mapResponseEntity.getBody());
        Integer status = Integer.parseInt(map.get("status").toString());
        if(status != 0) {
            return R.error("进件资料修改失败！");
        }
        tVyicooJinjian.setVerifyStatus(3); // 修改审核中
        tVyicooJinjianService.updateAllColumnById(tVyicooJinjian);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tvyicoojinjian:delete")
    @ApiOperation(value = "删除微易客进件", httpMethod = POST)
    public R delete(@RequestBody @ApiParam(name = "进件id数组", value = "typess") String[] types){
        tVyicooJinjianService.deleteBatchIds(Arrays.asList(types));

        return R.ok();
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

//        MultiValueMap<String, InputStream> postParameters = new LinkedMultiValueMap();
//        postParameters.add("file", inputStream);
//        HttpEntity<MultiValueMap<String, InputStream>> requestEntity = new HttpEntity(postParameters, null);
//        ResponseEntity<String> mapResponseEntity = restTemplate.postForEntity("https://pay.vyicoo.com/v3/common/upload", requestEntity, String.class);
//        Map map = JSONObject.parseObject(mapResponseEntity.getBody());
//        Integer status = Integer.parseInt(map.get("status").toString());
//        if(status != 0){
//            return R.error("微易客上传失败！");
//        }

        Boolean result = FTPUtils.storeFile(url, port, userName, password, vyicooPath, imageName, inputStream);
        if(!result) {
            return R.error("公司服务器上传失败！");
        }
        return R.ok().put("path", "/image/" + imageName).put("imageNginxPath", imageNginxPath);
    }

    public static Map<String,String> basicParam(Map<String,String> param) {
        param.put("app_id", "1000000067");
        param.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));
        param.put("version", "1.0");
        param.put("timestamp", System.currentTimeMillis() + "");
        param.put("sign", generateSignature(param,"9322948802806deea31a59edcdba31a38fd050dc"));
        return param;
    }

    public static String generateSignature(final Map<String, String> data, String key) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        return MD5(sb.toString()).toUpperCase();
    }
    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) {
        MessageDigest md;
        byte[] array = null;
        try {
            md = MessageDigest.getInstance("MD5");
            try {
                md.digest(data.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString().toUpperCase();
    }
}
