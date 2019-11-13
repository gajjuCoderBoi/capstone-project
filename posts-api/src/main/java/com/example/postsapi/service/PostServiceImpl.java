package com.example.postsapi.service;

import com.example.postsapi.model.Post;
import com.example.postsapi.repository.PostRepository;
import com.example.postsapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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

    public Post createPost(Post post, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();


        if (user==null){
            return null;
        }
        post.setUserId(user.getUserId());

        return postRepository.save(post);


    };

    public Long deletePost(Long postId, String token){
        Post deletePost = new Post();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();

        if (user==null){
            return null;
        }

        postRepository.deleteById(postId);

        return postId;



    };
}
