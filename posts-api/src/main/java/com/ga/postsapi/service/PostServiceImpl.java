package com.ga.postsapi.service;

import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import com.ga.postsapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PostRepository postRepository;


    @Override
    public Post createPost(Post post, String token) {
        User user = getUserFromUserAPI(token);
        if(user==null) return null;
        post.setUserId(user.getUserId());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Override
    public Long deletePost(Long postId, String token) {
        User user = getUserFromUserAPI(token);
        if(user==null) return null;
        Post savedPost = postRepository.findById(postId).orElse(null);
        if(savedPost.getUserId().longValue() != user.getUserId().longValue()) return null;
        postRepository.delete(savedPost);
        return savedPost.getPostId();
    }

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
        System.out.println(rateResponse);
        return savedPosts;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
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
}
