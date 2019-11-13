package com.ga.postsapi.service;

import com.ga.postsapi.model.Post;

public interface PostService {

    public Post createPost(Post post);

    public Post deletePost(Long postId);
}
