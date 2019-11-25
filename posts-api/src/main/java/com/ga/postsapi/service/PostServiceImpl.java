package com.ga.postsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {


    /*************************************************************************
     *
     *      Autowiring RestTemplate, just to make API calls from other services.
     *
     *      Autowiring PostRepository to handle data from CRUD Postgres SQL
     *      database.
     *
     *************************************************************************/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PostRepository postRepository;

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
     *      createPost is method with two params Post, Token(String) and Post
     *      return type. It will validate token from User-API service and
     *      get the User Object or null. And then set the userId of Post
     *      and save into database.
     *
     *************************************************************************/

    @Override
    public Post createPost(Post post, String token) throws TokenException {
        User user = getUserFromUserAPI(token);
        if (user == null) throw new TokenException("Invalid Token.");
        post.setUserId(user.getUserId());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    /*************************************************************************
     *
     *      deletePost is a method with postId(long) and Token(String) and
     *      Long return type.
     *
     *      This method validate token from User-API and matches userId from
     *      User and userId from Post and then delete Comments of that post
     *      by calling deleteCommentsOfPost.
     *
     *************************************************************************/

    @Override
    public Long deletePost(Long postId, String token) throws TokenException, UnauthorizeActionException, PostNotExistException {
        User user = getUserFromUserAPI(token);
        if (user == null) throw new TokenException("Invalid Token.");
        Post savedPost = postRepository.findById(postId).orElse(null);
        if(savedPost==null) throw new PostNotExistException("Post Doesn't Exist.");
        if (savedPost.getUserId().longValue() != user.getUserId().longValue()) throw new UnauthorizeActionException("Unauthorized Action.");
        deleteCommentsOfPost(savedPost.getPostId());
        postRepository.delete(savedPost);
        return savedPost.getPostId();
    }

    /*************************************************************************
     *
     *      postList is a method that will return all Posts exist into the
     *      database. And also collect Users of the Posts from the User-API
     *      and attached to related Posts.
     *
     *************************************************************************/

    @Override
    public List<Post> postList() {
        List<Post> savedPosts = (List<Post>) postRepository.findAll();
        Set<Long> userIdList = savedPosts.stream().map(Post::getUserId).collect(Collectors.toSet());
        String message = "";
        User[] rateResponse = null;
        try {
            message = objectMapper.writeValueAsString(userIdList);

            String response = (String) rabbitTemplate.convertSendAndReceive(postToUser.getName(), "usersList:"+message);

            rateResponse = objectMapper.readValue(response, User[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Long, User> userHashMap = new LinkedHashMap<>();
        for (User user : rateResponse) {
            userHashMap.put(user.getUserId(), user);
        }
        for (Post savedPost : savedPosts) {
            savedPost.setUser(userHashMap.get(savedPost.getUserId()));
        }
        return savedPosts;
    }

    /*************************************************************************
     *
     *      getPostById is a method that will return the specific Post by
     *      postId and also all the Comments liked to that specific Post.
     *
     *      Comments are collected from Comments-API by sending the postId.
     *
     *************************************************************************/

    @Override
    public Post getPostById(Long postId) {
        Post savedPost = postRepository.findById(postId).orElse(null);

        String res = (String) rabbitTemplate.convertSendAndReceive(this.postToComment.getName(), "findCommentsByPostId:" + postId);

        Comment[] comments = new Comment[0];
        try {
            comments = objectMapper.readValue(res, Comment[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        savedPost.setComments(Arrays.asList(comments));
        return savedPost;
    }

    /*************************************************************************
     *
     *      getUserFromUserAPI is responsible to send the token to the User-API
     *      and return the response, the response is either User model or NULL.
     *
     *************************************************************************/

    private User getUserFromUserAPI(String token) {
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
    private Long deleteCommentsOfPost(Long postId) {
        String res = (String) rabbitTemplate.convertSendAndReceive(this.postToComment.getName(), "deleteCommentsByPostId:" + postId);
        return Long.valueOf(res);
    }
}
