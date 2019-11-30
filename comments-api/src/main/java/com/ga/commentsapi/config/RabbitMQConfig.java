package com.ga.commentsapi.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*************************************************************************
 *
 *      RabbitMQConfig configures RabbitMQ. It creates three queues that will enable communication
 *      with the PostAPI and the UsersAPI
 *
 *************************************************************************/

@Configuration
public class RabbitMQConfig {

    @Bean("PostToComment")
    public Queue queue() {
        String QUEUE_NAME = "PostToComment";
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean("CommentToPost")
    public Queue commentToPostQueue() {
        String QUEUE_NAME = "CommentToPost";
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean("CommentToUser")
    public Queue commentToUserQueue(){
        String QUEUE_NAME = "CommentToUser";
        return new Queue(QUEUE_NAME, false, false, false);
    }

}