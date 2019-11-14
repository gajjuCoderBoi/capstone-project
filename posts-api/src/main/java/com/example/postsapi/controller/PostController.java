package com.example.postsapi.controller;

import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*************************************************************************
 *       Autowiring the
 *      Comment for Autowired
 *
 *************************************************************************/

@RestController
@RequestMapping
public class PostController {
    /*************************************************************************
     *      Autowiring the postService that will be used
     *      to handle the controller requests
     *
     *************************************************************************/

    @Autowired
    PostService postService;

    /*************************************************************************
     *   The createPost method takes two parameters: the postId and a token to
     *      create a post. It call the createPost method from postService
     *
     *************************************************************************/

    @PostMapping("")
    public Post createPost(Post post, String token){
        return postService.createPost( post, token);
    }


    /*************************************************************************
     *   The deletePost method takes two parameters: the postId and a token to
     *      delete a post. It call the deletePost method from postService
     *
     *************************************************************************/

    @DeleteMapping ("/post/{postId}")
    public Long deletePost(@PathVariable Long postId, String token){

        return postService.deletePost(postId, token);
    }

    /*************************************************************************
     *   The listPost method will find all posts
     *
     *
     *************************************************************************/
    @GetMapping ("/post/list")
    public List<Post> listPosts(){
        return postService.listPosts();
    }
}
