package com.ga.postsapi.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*************************************************************************
 *
 *      The Receiver class in the postsapi listens to the CommentToPost
 *      queue. It directs the message to a function in the postService
 *      class depending on the contents of the message
 *
 *************************************************************************/

@Component
public class Receiver {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "CommentToPost")
    public String messageHandler_CommentToPost(String message) throws JsonProcessingException {
        if(message.startsWith("getPostByPostId:")){
            Long postId = Long.valueOf(message.substring("getPostByPostId:".length()));
            Post post = postRepository.findById(postId).orElse(null);
            return post==null ?
                    "null" : objectMapper.writeValueAsString(post);
        }
        return null;
    }

}
