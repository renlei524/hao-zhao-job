package com.scxxwb.net.admin.common.socket;

import com.corundumstudio.socketio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * nettySocketIOServer
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.09
 */
public class NettySocketIOServer {
    @Value("${scxxwb.socket.host}")
    private String host;
    @Value("${scxxwb.socket.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
        config.setPort(this.port);
        config.setSocketConfig(new SocketConfig());
        config.setWorkerThreads(100);
        config.setAuthorizationListener(handshakeData -> true);
        final SocketIOServer server = new SocketIOServer(config);
        server.start();
        return server;
    }
}
