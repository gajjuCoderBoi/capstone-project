package com.ga.usersapi.messagequeue.receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.usersapi.model.User;
import com.ga.usersapi.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
//@RabbitListener(queues = {"UserToProfile", "PostToUser"})
public class Receiver {
    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "UserToProfile")
    public String messsageHandler_UserToProfile(String message) throws JsonProcessingException {
        String token = message;
        User user = null;
        try {
            user = userService.getUserByToken(token);
        } catch (Exception e) {
            System.out.println("UserToProfile: Invalid Token.");
            e.printStackTrace();
        }

        return user == null ? "null" : objectMapper.writeValueAsString(user);
    }

    @RabbitListener(queues = "PostToUser")
    public String messsageHandler_PostToUser(String message) throws IOException {

        if (message.startsWith("usersList:")) {
            return getUsersList(message);
        }
        if (message.startsWith("getUserByToken:")) {
            User user = null;
            try {
                String token = message.substring("getUserByToken:".length());
                user = userService.getUserByToken(token);
            } catch (Exception e) {
                System.out.println("PostToUser: Invalid Token.");
                //e.printStackTrace();
            }

            return user == null ? "null" : objectMapper.writeValueAsString(user);
        }
        return null;
    }

    @RabbitListener(queues = "CommentToUser")
    public String messsageHandler_CommentToUser(String message) throws IOException {
        if (message.startsWith("usersList:")) {
            return getUsersList(message);
        }
        if (message.startsWith("getUserByToken:")) {
            User user = null;
            try {
                String token = message.substring("getUserByToken:".length());
                user = userService.getUserByToken(token);
            } catch (Exception e) {
                System.out.println("PostToUser: Invalid Token.");
                //e.printStackTrace();
            }

            return user == null ? "null" : objectMapper.writeValueAsString(user);
        }
        return null;
    }

    private String getUsersList(String input) throws IOException {

        String userIdsString = input.substring("usersList:".length());
        Long[] userIds = objectMapper.readValue(userIdsString, Long[].class);
        List<User> users = userService.userListFromUserIds(Arrays.asList(userIds));
        return objectMapper.writeValueAsString(users);
    }

    private String getUserByToken(String input) throws JsonProcessingException {
        String token = input.substring("getUserByToken:".length());
        User user = userService.getUserByToken(token);
        return objectMapper.writeValueAsString(user);
    }

}
