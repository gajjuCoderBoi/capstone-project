package com.ga.postsapi.cofig;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean("PostToComment")
    public Queue postToCommentQueue() {
        String QUEUE_NAME = "PostToComment";
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean("PostToUser")
    public Queue postToUserQueue(){
        String QUEUE_NAME = "PostToUser";
        return new Queue(QUEUE_NAME, false, false, false);
    }
}