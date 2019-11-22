package com.ga.usersapi.messagequeue.receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.usersapi.model.User;
import com.ga.usersapi.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "UserToProfile")
public class Receiver {
    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitHandler
    public String messsageHandler(String message) throws JsonProcessingException {
        String token = message;
        User user = userService.getUserByToken(token);
        return objectMapper.writeValueAsString(user);
    }
}
