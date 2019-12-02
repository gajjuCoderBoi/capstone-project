package com.ga.postsapi.cofig;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
 * The RabbitMQConfig class sets up the three following queues for
 * microservice communication:
 * 1. postToCommentQueue()
 * 2. commentToPostQueue()
 * 3. postToUserQueue()
 *
 */
@Configuration
public class RabbitMQConfig {

    @Bean("PostToComment")
    public Queue postToCommentQueue() {
        String QUEUE_NAME = "PostToComment";
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean("CommentToPost")
    public Queue commentToPostQueue() {
        String QUEUE_NAME = "CommentToPost";
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean("PostToUser")
    public Queue postToUserQueue(){
        String QUEUE_NAME = "PostToUser";
        return new Queue(QUEUE_NAME, false, false, false);
    }
}