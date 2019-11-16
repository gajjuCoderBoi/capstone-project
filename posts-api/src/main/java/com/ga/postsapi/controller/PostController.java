package com.ga.postsapi.controller;

import com.ga.postsapi.model.Post;
import com.ga.postsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*************************************************************************
 *
 *      Comment for Autowired
 *
 *************************************************************************/

@RestController
@RequestMapping
public class PostController {
    /*************************************************************************
     *  postService is autowired. This is a necessary dependence so that we
     *  can call the services provided by PostService
     *
     *************************************************************************/

    @Autowired
    PostService postService;


    /*************************************************************************
     *  getPosts() is a method that takes no arguments. It will return a list of
     *  posts. It will accomplish this through a call to postServices function
     *  postList()
     *************************************************************************/
    @GetMapping("/list")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok(postService.postList());
    }


    /*************************************************************************
     *  getPostsById(Long postId) takes the PathVariable postId as an argument.
     *  It calls the postService.getPostById passing the postId. It will
     *  return the corresponding Posgt
     *************************************************************************/
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }



    /*************************************************************************
     *  The createPost method takes two arguments: a post and a token.
     *   The function will in turn call the postService method createPost passing
     *  these two values which will in turn create the post
     *************************************************************************/
    @PostMapping
    public Post createPost(@RequestBody Post post,@RequestHeader("Authorization") String token){
        return postService.createPost(post, token);
    }



    /*************************************************************************
     *  The deletePost method takes two parameters postId and token. The
     *  postService deletePost gets called receiving these two values and
     *  will further handle the deletePost request
     *************************************************************************/

    @DeleteMapping("/{postId}")
    public Long deletePost(@PathVariable Long postId,@RequestHeader("Authorization") String token){
        return postService.deletePost(postId, token);
    }


}
