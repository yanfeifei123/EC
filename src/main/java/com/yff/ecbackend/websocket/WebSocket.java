package com.yff.ecbackend.websocket;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import com.yff.ecbackend.messagequeue.service.OrderMessageThreadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {


    private static OrderMessageThreadingService orderMessageThreadingService;
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();
    private Session session;
    private String sid = "";

    @Autowired
    public  void setOrderMessageThreadingService(OrderMessageThreadingService orderMessageThreadingService) {
        WebSocket.orderMessageThreadingService = orderMessageThreadingService;
    }



    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.sid = this.session.getId();
        webSocketSet.put(this.sid,this);
        log.info("【websocket消息】有新的连接, 总数:{}，sessionid:{}", webSocketSet.size(),this.session.getId());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this.sid);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
        if(ToolUtil.isNotEmpty(message)){
            OrderMessageTemplate orderMessageTemplate = orderMessageThreadingService.take(Long.valueOf(message));
            if(ToolUtil.isNotEmpty(orderMessageTemplate)){
                this.sendMessage(JSON.toJSONString(orderMessageTemplate));
            }
        }
    }

    public void sendMessage(String message) {
        WebSocket webSocket = webSocketSet.get(this.sid);
        if(ToolUtil.isNotEmpty(webSocket)){
            log.info("【websocket消息】发送消息, message={},sessionid:{}", message,this.sid);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
