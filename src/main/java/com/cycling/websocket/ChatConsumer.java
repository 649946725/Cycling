package com.cycling.websocket;

import com.cycling.pojo.Chat;
import com.cycling.rabbitmq.RabbitMqConfig;
import com.cycling.service.UserService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author xpdxz
 * @ClassName ChatConsumer
 * @Description TODO
 * @Date 2022/3/13 19:37
 */

@Service
public class ChatConsumer {

    @Resource
    private UserService userService;

    @RabbitListener(queues = RabbitMqConfig.NAME)
    public void saveChatConsumer(Chat chat, @Header(AmqpHeaders.DELIVERY_TAG) long delivery, Channel channel) {
        try {
            if (chat != null) {
                userService.insertChatHistory(chat);
            }
            channel.basicAck(delivery, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
