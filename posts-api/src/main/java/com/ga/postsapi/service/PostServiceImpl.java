package com.ga.postsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.messagequeue.Sender;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
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
    private Sender sender;

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
        User user = sender.getUserFromUserAPI(token);
        if (user == null) {
            logger.info("Invalid token: "+ token  +" User could not be retrieved");
            throw new TokenException("Invalid Token.");};
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
        User user = sender.getUserFromUserAPI(token);
        if (user == null) {
            logger.info("Invalid token: " + token + " User could not be found");
            throw new TokenException("Invalid Token.");};
        Post savedPost = postRepository.findById(postId).orElse(null);
        if(savedPost==null)
        {
            logger.info("Post not found for this postId: " + postId +  " Delete operation could not be performed");
            throw new PostNotExistException("Post Doesn't Exist.");};
        if (savedPost.getUserId().longValue() != user.getUserId().longValue()) throw new UnauthorizeActionException("Unauthorized Action.");
        sender.deleteCommentsOfPost(savedPost.getPostId());
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

        User[] rateResponse = sender.getUsersByUserId(userIdList);

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

        Comment[] comments = sender.findCommentsByPostId(savedPost.getPostId());

        savedPost.setComments(Arrays.asList(comments));
        return savedPost;
    }

    @Override
    public List<Post> getPostsByUser(String token) throws TokenException {
        User user = sender.getUserFromUserAPI(token);
        if(user==null) throw new TokenException("Invalid Credentials.");
        List<Post> postList = (List<Post>) postRepository.getPostsByUserId(user.getUserId());
        return postList;

    }


}
