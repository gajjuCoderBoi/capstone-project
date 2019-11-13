package com.example.postsapi.service;

import com.example.postsapi.model.Post;

public interface PostService {

    public Post createPost(Post post);

    public Post deletePost(Long postId);
}
