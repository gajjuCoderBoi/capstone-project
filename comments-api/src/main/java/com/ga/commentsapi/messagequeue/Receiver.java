package com.ga.commentsapi.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.service.CommentService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(queues = "PostToComment")
public class Receiver {

    @Autowired
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitHandler
    public String messageHandler(String message) throws JsonProcessingException {
        if(message.startsWith("findCommentsByPostId:")){
            long postId = Long.parseLong(message.substring("findCommentsByPostId:".length()));
            System.out.println("findCommentsByPostId:"+postId);
            List<Comment> comments= (List<Comment>) commentService.getCommentsbyPostId(postId);
            return objectMapper.writeValueAsString(comments);
        }
        else if (message.startsWith("deleteCommentsByPostId:")){
            long postId = Long.parseLong(message.substring("deleteCommentsByPostId:".length()));
            System.out.println("deleteCommentsByPostId:"+postId);
            return String.valueOf(commentService.deleteCommentsByPostId(postId));

        }
        return "";
    }


}
