package leiren.haozhaojob.modules.operation.controller;

import java.util.Map;
import leiren.haozhaojob.modules.operation.service.UserWeiBaoService;
import leiren.haozhaojob.common.utils.PageUtils;
import leiren.haozhaojob.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-15 13:59:26
 */
@RestController
@RequestMapping("operation/userweibao")
public class UserWeibaoController {
    @Autowired
    private UserWeiBaoService userWeiBaoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:userweibao:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userWeiBaoService.queryPage(params);

        return R.ok().put("page", page);
    }
}
