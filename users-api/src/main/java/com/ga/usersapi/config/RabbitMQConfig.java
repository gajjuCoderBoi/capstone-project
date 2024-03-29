package com.ga.usersapi.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*******************************************************************
 * The RabbitMQConfig class sets up the following queues for
 * microservices communication:
 * 1. userToProfileQueue()
 * 2. postToUserQueue()
 * 3. commentToUserQueue()
 */

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userToProfileQueue(){
        String QUEUE_NAME = "UserToProfile";
        return new Queue(QUEUE_NAME, false, false, false);
    }
    @Bean("PostToUser")
    public Queue postToUserQueue(){
        String QUEUE_NAME = "PostToUser";
        return new Queue(QUEUE_NAME, false, false, false);
    }
    @Bean("CommentToUser")
    public Queue commentToUserQueue(){
        String QUEUE_NAME = "CommentToUser";
        return new Queue(QUEUE_NAME, false, false, false);
    }
}
