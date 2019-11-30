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

/*************************************************************************
 *
 *      The Sender class sends messages to the queues commentToUserQueue
 *      and commentToPostQueue. It has three methods:
 *
 *      1. getUserFromUserAPI(String token)
 *      2. getPostByPostId(Long postId)
 *      3. getUsersByUsersId(Set<Long> userIdList)
 *
 *************************************************************************/




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


    /*************************************************************************
     * the getPostsByPostID takes a postId as argument. It is an auxiliary
     * method that returns a Post using its postId
     *************************************************************************/
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

    /*************************************************************************
     * the getUsersByUsersId takes a list of userIdList as argument. It is an auxiliary
     * method that returns the users to whom the userIds belong
     *************************************************************************/
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
