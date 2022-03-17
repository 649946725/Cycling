package com.cycling.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @Author xpdxz
 * @ClassName WebSocketConfig
 * @Description TODO
 * @Date 2022/3/13 19:56
 */
@Configuration
public class WebSocketConfig implements InitializingBean {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ObjectMapper jsonMapper;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        WebSocketService.setRabbitTemplate(rabbitTemplate);
        WebSocketService.setJsonMapper(jsonMapper);
    }
}
