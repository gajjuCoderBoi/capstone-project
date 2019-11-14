package com.ga.commentsapi.service;

import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.model.User;

import com.ga.commentsapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class CommentServiceImpl {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommentRepository commentRepository;

    public Comment createComment(Comment comment, Long postId, String token){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();

        if (user==null){
            return null;
        }
        comment.setUserId(user.getUserId());

        comment.setCommentId(postId);

        return commentRepository.save(comment);


    };

    public void getCommentsbyPostId(Long PostId){


    };

    public void deleteCommentByCommentId(Long commentId, String token){
        commentRepository.deleteById(commentId);

    };


    public void getCommentsByUser(User user){

    };
}
