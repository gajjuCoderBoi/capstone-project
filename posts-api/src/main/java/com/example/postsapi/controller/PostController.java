package com.example.postsapi.controller;

import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*************************************************************************
 *       todo
 *      Comment for Autowired
 *
 *************************************************************************/

@RestController
@RequestMapping
public class PostController {
    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @Autowired
    PostService postService;

    @PostMapping("/post")
    public Post createPost(Post post, String token){
        return postService.createPost( post, token);
    }
    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @DeleteMapping ("/post/{postId}")
    public Long deletePost(@PathVariable Long postId, String token){

        return postService.deletePost(postId, token);
    }
}
