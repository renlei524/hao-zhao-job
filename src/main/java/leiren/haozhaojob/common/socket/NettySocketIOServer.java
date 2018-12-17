package leiren.haozhaojob.common.socket;

import com.corundumstudio.socketio.*;
import leiren.haozhaojob.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * nettySocketIOServer
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.09
 */
@Component
public class NettySocketIOServer {
    @Value("${scxxwb.socket.host}")
    private String host;
    @Value("${scxxwb.socket.port}")
    private Integer port;

    @Resource
    RedisUtils redisUtils;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
        config.setHostname(host);
        config.setPort(port);
        config.setSocketConfig(new SocketConfig());
        config.setWorkerThreads(100);
        config.setAuthorizationListener(handshakeData -> true);
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(socketIOClient -> {
            String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
            UUID uuid = new UUID(socketIOClient.getSessionId().getMostSignificantBits(), socketIOClient.getSessionId().getLeastSignificantBits());
            UUID redisUUID = redisUtils.get(userName + host, UUID.class);
            if(redisUUID == null) {
                redisUtils.set(userName + host, uuid, 60 * 60 * 1000);
            } else if(!uuid.toString().equals(redisUUID.toString())){
                server.getClient(redisUUID).sendEvent("loginOut", "账号在其他设备登录，强制退出！");
                redisUtils.set(userName + host, uuid, 60 * 60 * 1000);
            }
        });
        server.addDisconnectListener(socketIOClient -> {
            String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
            redisUtils.delete(userName + host);
        });
        server.start();
        return server;
    }
}
