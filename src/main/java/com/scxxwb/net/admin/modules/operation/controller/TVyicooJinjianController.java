package com.scxxwb.net.admin.modules.operation.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.scxxwb.net.admin.common.utils.DateUtils;
import com.scxxwb.net.admin.common.utils.FTPUtils;
import com.scxxwb.net.admin.common.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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


/**
 *  微易客进件
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
@Api(value = "operation/tvyicoojinjian", description = "微易客进件")
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
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tVyicooJinjianService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{type}")
    @RequiresPermissions("operation:tvyicoojinjian:info")
    public R info(@PathVariable("type") String type){
        TVyicooJinjianEntity tVyicooJinjian = tVyicooJinjianService.selectById(type);

        return R.ok().put("tVyicooJinjian", tVyicooJinjian);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:tvyicoojinjian:save")
    public R save(@RequestBody TVyicooJinjianEntity tVyicooJinjian){
        tVyicooJinjianService.insert(tVyicooJinjian);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:tvyicoojinjian:update")
    public R update(@RequestBody TVyicooJinjianEntity tVyicooJinjian){
        ValidatorUtils.validateEntity(tVyicooJinjian);
        tVyicooJinjianService.updateAllColumnById(tVyicooJinjian);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:tvyicoojinjian:delete")
    public R delete(@RequestBody String[] types){
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

        MultiValueMap<String, InputStream> postParameters = new LinkedMultiValueMap();
        postParameters.add("file", inputStream);
        HttpEntity<MultiValueMap<String, InputStream>> requestEntity = new HttpEntity(postParameters, null);
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity("https://pay.vyicoo.com/v3/common/upload", requestEntity, Map.class);
        Map map = mapResponseEntity.getBody();
        Integer status = Integer.parseInt(map.get("status").toString());
        if(status != 0){
            return R.error("微易客上传失败！");
        }

        Boolean result = FTPUtils.storeFile(url, port, userName, password, vyicooPath, imageName, inputStream);
        if(!result) {
            return R.error("公司服务器上传失败！");
        }
        return R.ok().put("path", "/image/" + imageName).put("imageNginxPath", imageNginxPath);
    }
}
