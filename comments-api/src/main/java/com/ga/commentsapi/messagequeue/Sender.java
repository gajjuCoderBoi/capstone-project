package com.ga.commentsapi.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.bean.Post;
import com.ga.commentsapi.bean.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class Sender {
    @Autowired
    @Qualifier("CommentToUser")
    private Queue commentToUserQueue;

    @Autowired
    @Qualifier("CommentToPost")
    private Queue commentToPostQueue;


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;
    /*************************************************************************
     * the getUserFromUserAPI takes a token as argument. It is an auxiliary
     * method that returns the user that owns the token
     *************************************************************************/

    public User getUserFromUserAPI(String token) {
        String response = (String) rabbitTemplate.convertSendAndReceive(commentToUserQueue.getName(), "getUserByToken:" + token);
        User user = null;
        try {
            user = response.equals("null") ? null :  objectMapper.readValue(response,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Post getPostByPostId(Long postId){
        String response = (String) rabbitTemplate.convertSendAndReceive(commentToPostQueue.getName(), "getPostByPostId:" + postId);
        Post post = null;

        try {
            post = response.equals("null") ? null : objectMapper.readValue(response, Post.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public User[] getUsersByUsersId(Set<Long> userIdList){
        String message = "";
        User[] rateResponse = null;
        try {
            message = objectMapper.writeValueAsString(userIdList);

            String response = (String) rabbitTemplate.convertSendAndReceive(commentToUserQueue.getName(), "usersList:" + message);

            rateResponse = objectMapper.readValue(response, User[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rateResponse;
    }
}
