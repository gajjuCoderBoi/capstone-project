package com.ga.profileapi.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.profileapi.bean.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    @Autowired
    ObjectMapper objectMapper;
    /*************************************************************************
     *
     *      getUserFromUserAPI is responsible to send the token to the User-API
     *      and return the response, the response is either User model or NULL.
     *
     *************************************************************************/

    public User getUserFromUserAPI(String token) {
        String response = (String) rabbitTemplate.convertSendAndReceive(queue.getName(), token);
        User user=null;
        try {
            user = response.equals("null") ? null :  objectMapper.readValue(response,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
