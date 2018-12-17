package leiren.haozhaojob.modules.sys.controller;


import leiren.haozhaojob.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("sys/job")
public class JobController extends AbstractController {
    @Value(value = "${scxxwb.elasticJob.url}")
    private String url;

    /**
     * 任务地址
     */
    @RequestMapping("/index")
    public R index(){
        return R.ok().put("url", url);
    }
}
