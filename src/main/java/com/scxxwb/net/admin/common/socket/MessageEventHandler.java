package com.scxxwb.net.admin.common.socket;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

/**
 * MessageEventHandler
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.09
 */
@Component
public class MessageEventHandler {
    public Map<String, SocketIOClient> noClientMap = new HashMap();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userName = client.getHandshakeData().getSingleUrlParam("userName");
        if (!StringUtils.isEmpty(userName)) {
            noClientMap.put(userName, client);
        }
    }

    @OnDisconnect
    public void onDisConnect(SocketIOClient client) {
        String userName = client.getHandshakeData().getSingleUrlParam("userName");
        if (!StringUtils.isEmpty(userName)) {
            noClientMap.remove(userName, client);
        }

    }

    @OnEvent(value = "noEvent")
    public void onEvent(SocketIOClient client, String data, AckRequest request) {
        if (!StringUtils.isEmpty(data)) {
            noClientMap.put(data, client);
        }
    }

    public void toOne(String userName, String eventName, Object data) {
        SocketIOClient socketIOClient = noClientMap.get(userName);
        if (socketIOClient != null) {
            try {
                // 推送消息即为调用SocketIOClient的sendEvent方法
                socketIOClient.sendEvent(eventName, data);
            } catch (Exception e) {
            }
        }
    }

    public void toAll(Object data) {
        for (String userName : noClientMap.keySet()) {
            toOne(userName, "taskResult", data);
        }
    }
}
