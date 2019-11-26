package com.ga.postsapi.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.bean.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @Qualifier("PostToComment")
    private Queue postToComment;

    @Autowired
    @Qualifier("PostToUser")
    private Queue postToUser;

    /*************************************************************************
     *
     *      getUserFromUserAPI is responsible to send the token to the User-API
     *      and return the response, the response is either User model or NULL.
     *
     *************************************************************************/

    public User getUserFromUserAPI(String token) {
        String response = (String) rabbitTemplate.convertSendAndReceive(postToUser.getName(), "getUserByToken:"+token);
        User user=null;
        try {
            user = response.equals("null") ? null :  objectMapper.readValue(response,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }


    /*************************************************************************
     *
     *      deleteCommentsOfPost is a helper method that calls the delete api
     *      of the Comments-API which delete all the comments related to the
     *      postId.
     *
     *************************************************************************/
    public Long deleteCommentsOfPost(Long postId) {
        String res = (String) rabbitTemplate.convertSendAndReceive(this.postToComment.getName(), "deleteCommentsByPostId:" + postId);
        return Long.valueOf(res);
    }
}