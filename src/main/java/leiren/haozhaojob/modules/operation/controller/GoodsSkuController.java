package leiren.haozhaojob.modules.operation.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

import leiren.haozhaojob.common.annotation.SysLog;
import leiren.haozhaojob.common.validator.ValidatorUtils;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;
import leiren.haozhaojob.modules.operation.entity.GoodsSkuEntity;
import leiren.haozhaojob.modules.operation.service.GoodsSkuService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.modules.sys.shiro.ShiroUtils;
import leiren.haozhaojob.common.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
@RestController
@RequestMapping("operation/goodsSku")
public class GoodsSkuController extends AbstractController {
    @Value(value = "${scxxwb.ftp.url}")
    private String url;
    @Value(value = "${scxxwb.ftp.port}")
    private Integer port;
    @Value(value = "${scxxwb.ftp.user-name}")
    private String userName;
    @Value(value = "${scxxwb.ftp.password}")
    private String password;
    @Value(value = "${scxxwb.ftp.goodsPath}")
    private String goodsPath;
    @Value(value = "${scxxwb.nginx.goodsPath}")
    private String goodsNginxPath;

    String sheetName = "商品种类";

    @Autowired
    private GoodsSkuService goodsSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:goodsSku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:goodsSku:info")
    public R info(@PathVariable("id") Integer id){
        GoodsSkuEntity goodsSku = goodsSkuService.selectById(id);

        return R.ok().put("goodsSku", goodsSku);
    }

    /**
     * 保存
     */
    @SysLog("保存商品")
    @RequestMapping("/save")
    @RequiresPermissions("operation:goodsSku:save")
    public R save(@RequestBody GoodsSkuEntity skuGoods){
        skuGoods.setStatus(1);
        //添加创建人
        skuGoods.setUserId(ShiroUtils.getUserEntity().getUserId().intValue());
        skuGoods.setCreateTime(new Date());
        ValidatorUtils.validateEntity(skuGoods, AddGroup.class);
        goodsSkuService.insert(skuGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改商品")
    @RequestMapping("/update")
    @RequiresPermissions("operation:goodsSku:update")
    public R update(@RequestBody GoodsSkuEntity skuGoods){
        ValidatorUtils.validateEntity(skuGoods, UpdateGroup.class);
        goodsSkuService.updateAllColumnById(skuGoods);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除商品")
    @RequestMapping("/delete")
    @RequiresPermissions("operation:goodsSku:delete")
    public R delete(@RequestBody Integer[] ids){
        goodsSkuService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导入
     */
    @RequestMapping("/import")
    @RequiresPermissions("operation:goodsSku:import")
    public R importGoodsSku(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            ExcelUtils<GoodsSkuEntity> entityExcelUtils = new ExcelUtils<>();
            List<GoodsSkuEntity> goodsSkuEntityList = entityExcelUtils.importFromExcel(file.getInputStream(), sheetName, GoodsSkuEntity.class, entityExcelUtils.getExcelToFielMap(GoodsSkuEntity.class));
            goodsSkuService.insertOrUpdateBatch(goodsSkuEntityList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 导出
     */
    @RequestMapping("/export")
    @RequiresPermissions("operation:goodsSku:export")
    public void exportGoodsSku(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        List<GoodsSkuEntity>  goodsSkuEntityList = goodsSkuService.selectByMap(params);
        String fileName = "商品种类";
        try {
            String userAgent = request.getHeader("User-Agent");
            //处理各大奇葩浏览器下载中文名字可能会乱码的兼容问题
            boolean isMSIE = (userAgent != null && userAgent.indexOf("MSIE") != -1);
            if (isMSIE) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else  fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName +".xls");
            response.setContentType("application/x-download");

            ExcelUtils<GoodsSkuEntity> entityExcelUtils = new ExcelUtils<>();
            HSSFWorkbook workbook = entityExcelUtils.exportToExcel(entityExcelUtils.getFieldToExcelMap(GoodsSkuEntity.class), null, goodsSkuEntityList, sheetName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * imageBase64文件上传
     */
    //@SysLog("商品imageBase64文件上传")
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

        Boolean result = FTPUtils.storeFile(url, port, userName, password, goodsPath, imageName, inputStream);
        if(!result) {
            return R.error();
        }
        //return R.ok().put("path", imageNginxPath + "/" + imageName);
        return R.ok().put("path", "/image/" + imageName).put("imageNginxPath", goodsNginxPath);
    }
}
