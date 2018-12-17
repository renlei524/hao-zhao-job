package leiren.haozhaojob.modules.sys.controller;

import leiren.haozhaojob.common.utils.R;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ES管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@RestController
@RequestMapping("sys/es")
public class EsController extends AbstractController {
    @Value(value = "${scxxwb.elasticSearch.url}")
    private String url;
    @Value(value = "${scxxwb.elasticSearch.esUserName}")
    private String esUserName;
    @Value(value = "${scxxwb.elasticSearch.esPassword}")
    private String esPassword;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * es地址
     */
    @RequestMapping("/login")
    public R login(@RequestParam Map<String, Object> params, HttpServletRequest request){
        if(params.isEmpty()) {
            if(redisTemplate.opsForValue().get("esSession:" + request.getRequestedSessionId()) != null) {
                return R.ok().put("url", url);
            }
            if(redisTemplate.opsForValue().get("esSession:" + request.getRequestedSessionId()) == null) {
                return R.error();
            }
        }
        if(esUserName.equals(params.get("esUserName").toString()) && esPassword.equals(params.get("esPassword").toString())) {
            redisTemplate.opsForValue().set("esSession:" + request.getRequestedSessionId(), esUserName, 60, TimeUnit.MINUTES);
            return R.ok().put("url", url);
        }
        return R.error("用户名或密码不正确！");
    }
}
