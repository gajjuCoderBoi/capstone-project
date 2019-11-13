package com.ga.postsapi.controller;

import com.ga.postsapi.model.Post;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PostController {

    @PostMapping("/post")
    public Post createPost(){
        return null;
    }

    @DeleteMapping ("/delete")
    public Post deletePost(){
        return null;
    }
}
