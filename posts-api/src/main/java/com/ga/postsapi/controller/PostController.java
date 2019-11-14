package com.ga.postsapi.controller;

import com.ga.postsapi.model.Post;
import com.ga.postsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/
    @GetMapping("/list")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok(postService.postList());
    }

    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }



    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/
    @PostMapping
    public Post createPost(@RequestBody Post post,@RequestHeader("Authorization") String token){
        return postService.createPost(post, token);
    }
    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @DeleteMapping("/{postId}")
    public Long deletePost(@PathVariable Long postId, String token){
        return postService.deletePost(postId, token);
    }


}
