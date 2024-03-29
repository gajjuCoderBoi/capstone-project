package com.ga.profileapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*******************************************************************
* The RabbitMQConfig class sets up the following queue for
* microservice communication: UserToProfile
*
*/

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue(){
        String QUEUE_NAME = "UserToProfile";
        return new Queue(QUEUE_NAME, false, false, false);
    }
}
