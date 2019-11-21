package com.ga.commentsapi.messagequeue.receiver;

import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.service.CommentService;
import org.springframework.amqp.rabbit.annotation.Queue;
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

    @RabbitHandler
    public String findCommentsByPostId(String postId){
        System.out.println("I recieved this PostId:"+postId);
        List<Comment> comments= (List<Comment>) commentService.getCommentsbyPostId(Long.valueOf(postId));
        return String.valueOf(comments);
    }

}
