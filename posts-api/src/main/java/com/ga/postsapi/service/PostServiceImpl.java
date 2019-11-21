package com.ga.postsapi.service;

import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import com.ga.postsapi.bean.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    @Qualifier("PostToComment")
    private Queue queue;

    /*************************************************************************
     *
     *      createPost is method with two params Post, Token(String) and Post
     *      return type. It will validate token from User-API service and
     *      get the User Object or null. And then set the userId of Post
     *      and save into database.
     *
     *************************************************************************/

    @Override
    public Post createPost(Post post, String token) {
        User user = getUserFromUserAPI(token);
        if(user==null) return null;
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
    public Long deletePost(Long postId, String token) {
        User user = getUserFromUserAPI(token);
        if(user==null) return null;
        Post savedPost = postRepository.findById(postId).orElse(null);
        if(savedPost.getUserId().longValue() != user.getUserId().longValue()) return null;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Set<Long>> requestEntity = new HttpEntity<Set<Long>>(userIdList,headers);
        User[] rateResponse = restTemplate.exchange("http://users-api:5001/userlist", HttpMethod.POST, requestEntity,User[].class).getBody();
        HashMap<Long, User> userHashMap = new LinkedHashMap<>();
        for (User user:rateResponse){
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


        System.out.println("Sending message...");
        String res = (String) rabbitTemplate.convertSendAndReceive(this.queue.getName(), String.valueOf(postId));
        System.out.println("Message sent: " + String.valueOf(postId) + " on q: " + queue.getName());
        System.out.println("Message Recieved:"+res);


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Comment[] comments =  restTemplate.exchange("http://comments-api:5003/"+savedPost.getPostId(), HttpMethod.GET, entity, Comment[].class).getBody();
        savedPost.setComments(Arrays.asList(comments));
        return savedPost;
    }

    /*************************************************************************
     *
     *      getUserFromUserAPI is responsible to send the token to the User-API
     *      and return the response, the response is either User model or NULL.
     *
     *************************************************************************/

    private User getUserFromUserAPI(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();
    }


    /*************************************************************************
     *
     *      deleteCommentsOfPost is a helper method that calls the delete api
     *      of the Comments-API which delete all the comments related to the
     *      postId.
     *
     *************************************************************************/
    private Long deleteCommentsOfPost(Long postId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Long res =  restTemplate.exchange("http://comments-api:5003/"+postId+"/comments", HttpMethod.DELETE, entity, Long.class).getBody();
        return res;
    }
}
