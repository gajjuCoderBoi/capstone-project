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
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    /*************************************************************************
     *
     *      Autowiring the RestTemplate, PostRepository
     *      These are the dependencies for the Post services and used in
     *      this service
     *
     *************************************************************************/
    @Autowired
    RestTemplate restTemplate;



    @Autowired
    PostRepository postRepository;

    /*************************************************************************
     * The createPost method will create a post. It takes two parameters:
     * a post,  and a token
     * from which the comment will get the userid of the user who is posting the
     * comment. It uses the HttpHeaders to verify the token
     *
     *************************************************************************/
    @Override
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

    /*************************************************************************
     * The deletePost gets two arguments: a postId (postId
     * belongs to the post that will be deleted and a token to validate
     * the authority of the user who is trying to delete
     *************************************************************************/
    @Override
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

    @Override
    public List<Post> listPosts(){
        return (List<Post>) postRepository.findAll();
    };
}
