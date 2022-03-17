package com.cycling.websocket;

import com.cycling.pojo.Chat;
import com.cycling.rabbitmq.RabbitMqConfig;
import com.cycling.utils.RedisUtil;
import com.cycling.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xpdxz
 * @ClassName WebSocketService
 * @Description TODO
 * @Date 2022/3/6 17:58
 */

@ServerEndpoint(value = "/cycling/{userId}")
@Service
public class WebSocketService {

    /**
     * 使用redis存储在线人数
     */
    private static final String ONLINE_KEY = "online";

    private static final Map<String, Session> ONLINE = new ConcurrentHashMap<>();

    private String id;

    private static ObjectMapper JSON_MAPPER;

    private static RabbitTemplate rabbitTemplate;

    public static void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        WebSocketService.rabbitTemplate = rabbitTemplate;
    }

    public static void setJsonMapper(ObjectMapper jsonMapper) {
        WebSocketService.JSON_MAPPER = jsonMapper;
    }

    /**
     * 链接打开
     *
     * @param userId
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        this.id = userId;
        ONLINE.put(userId, session);
        System.out.println(ONLINE);
        RedisUtil.incr(ONLINE_KEY, 1);
    }

    /**
     * 接收客户端信息
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Chat chat = JSON_MAPPER.readValue(message, Chat.class);
        Long receiver = chat.getReceiver();
        Session sessionReceiver = ONLINE.get(String.valueOf(receiver));
        if (sessionReceiver != null) {
            sessionReceiver.getBasicRemote().sendText(message);
            session.getBasicRemote().sendText(JSON_MAPPER.writeValueAsString(ResponseResult.ok()));
            rabbitTemplate.convertAndSend(RabbitMqConfig.NAME, chat);
        }
    }

    @OnClose
    public void onClose(Session session) {
        RedisUtil.decr(ONLINE_KEY, 1);
        ONLINE.remove(id);
    }

    @OnError
    public void onError(Throwable throwable) {
        RedisUtil.decr(ONLINE_KEY, 1);
        System.out.println(throwable.getMessage());
        ONLINE.remove(id);
    }


}
