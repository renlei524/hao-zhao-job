package com.scxxwb.net.admin.modules.operation.controller;

import com.alibaba.fastjson.JSON;
import com.scxxwb.net.admin.common.utils.*;
import com.scxxwb.net.admin.modules.operation.entity.JinJianEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户进件管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("operation/jinjian")
public class JinJianController {
    public final static String uploadUrl = "https://nsposf.cloudpnr.com/nsposfweb/webB9002/uploadWebMerPic";
    public final static String sendUlr = "http://pttest.chinapnr.com/nsposmweb/webB1412";

    private final static String BASEPATH = "/static/image/";
    private final static String WEBURL = "192.168.10.245:8686/image/";

    /**
     * 进件图片上传
     */
    @RequestMapping("/upload")
    @RequiresPermissions("operation:jinjian:upload")
    public R upload(String base64Str) {
        //图片
        String avaterBase64 = base64Str.substring(base64Str.indexOf(",") + 1);
        String avater = System.currentTimeMillis() + ".jpg";
        Base64Utils.GenerateImage(avaterBase64, avater);

        String localFile =  BASEPATH + avater;
        File file = new File(localFile);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(uploadUrl);
            httpPost.setHeader("charset", "utf-8");
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            mEntityBuilder.addBinaryBody("picture", file);
            ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("agentId", "310000015000017897");
            map.put("photoType", "01");
            map.put("reqSerialNum", "1234567890abc");
            String returnStr = JSON.toJSONString(map);

            mEntityBuilder.addTextBody("jsonData", returnStr,contentType);
            mEntityBuilder.addTextBody("checkValue", ShaUtil.encode(returnStr, "chinapnr", "UTF-8"),contentType);
            httpPost.setEntity(mEntityBuilder.build());
            response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result =EntityUtils.toString(resEntity);
                // 消耗掉response
                EntityUtils.consume(resEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(httpclient);
            HttpClientUtils.closeQuietly(response);
        }

        return R.ok().put("path", WEBURL + avater);
    }

    /**
     商品进件
     */
    @RequestMapping("/send")
    @RequiresPermissions("operation:jinjian:send")
    public R send(@RequestBody JinJianEntity jinJianEntity) {
        String json = "{\"licType\":\"1\",\"cityId\":\"泉州市\",\"signDate\":\"20180101\",\"bankAreaId\":\"丰泽区\",\"settleTerm\":\"1\",\"idNo\":\"350426198610077016\",\"regAddr\":\"丰泽区津淮街基金楼大厦2506\",\"legalName\":\"王医\",\"isCreditCode\":\"1\",\"accountIdNo\":\"350426198610077016\",\"tellerId\":\"MH13806007141\",\"accountIdEdate\":\"20301231\",\"userDefinedName\":\"闽汇信息技术\",\"idSdate\":\"20171201\",\"contactIdNo\":\"350426198610077016\",\"merchClass\":\"1\",\"merchAddr\":\"丰泽区津淮街基金楼大厦2506\",\"businessEhours\":\"23:00\",\"bankCityId\":\"泉州市\",\"memberId\":\"030147446000003710\",\"idType\":\"01\",\"merchName\":\"泉州市闽汇信息技术有限公司\",\"pnrpayMerType\":\"7\",\"licSdate\":\"20170517\",\"wechatCateCode\":\"288\",\"bankActName\":\"王医\",\"accountIdType\":\"01\",\"idEdate\":\"20301231\",\"contactIdValidType\":\"1\",\"opTellerId\":\"lxlx002\",\"idValidType\":\"1\",\"merchShortName\":\"闽汇信息技术\",\"bankActId\":\"6226222380418397\",\"signEdate\":\"20220101\",\"fee06\":\"0.60\",\"fee08\":\"0.35\",\"auth23\":\"1\",\"fee02\":\"0.45\",\"bankProvId\":\"福建\",\"fee03\":\"0.60\",\"wechatPubNum\":\"wx73bc509820fa4ce3\",\"accountIdSdate\":\"20170517\",\"auth24\":\"0\",\"fee05\":\"0.45\",\"merchType\":\"2\",\"businessShours\":\"06:00\",\"bankName\":\"中国民生银行\",\"isPrivate\":\"1\",\"chargeCateCode\":\"1\",\"creditCode\":\"91350503MA346U3771\",\"cateType\":\"1\",\"contactTelno\":\"13806007141\",\"accountIdValidType\":\"1\",\"contactIdEdate\":\"20301231\",\"isSendMes\":\"0\",\"regCityId\":\"泉州市\",\"wechatPubNumAppid\":\"wx73bc509820fa4ce3\",\"auth18\":\"1\",\"signName\":\"王冬冬\",\"contactEmail\":\"kfduijie@shoukuanle.net\",\"isMerWechat\":\"0\",\"regProvId\":\"福建\",\"fee12\":\"0.35\",\"contactIdSdate\":\"20170517\",\"regAreaId\":\"丰泽区\",\"alipayCateCode\":\"2016062900190337\",\"areaId\":\"丰泽区\",\"licEdate\":\"20470517\",\"contractNum\":\"AB10000002\",\"contactIdType\":\"01\",\"provId\":\"福建\",\"auth19\":\"1\",\"picUploadWay\":\"01\",\"wechatPubNumAuth\":\"https://shoukuanle.shop/skl/js/views/qrpay/\"}";
        //String json = jinJianEntity.getJsonData();
        String checkValue = ShaUtil.encode(json, "chinapnr", HttpRequestConstant.UTF_8);
        String result =  new HttpRequest().sendPost(sendUlr, "checkValue=" + checkValue + "&jsonData=" + json);

        return R.ok().put("result", result);
    }
}
