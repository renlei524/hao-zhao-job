package leiren.haozhaojob.modules.operation.controller;

import java.util.Map;

import leiren.haozhaojob.modules.operation.entity.GoodsEntity;
import leiren.haozhaojob.modules.operation.service.GoodsService;
import leiren.haozhaojob.modules.sys.controller.AbstractController;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品信息
 *
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
@RestController
@RequestMapping("operation/goods")
public class GoodsController extends AbstractController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:goods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:goods:info")
    public R info(@PathVariable("id") Integer id){
        GoodsEntity tGoods = goodsService.selectById(id);

        return R.ok().put("tGoods", tGoods);
    }
}
