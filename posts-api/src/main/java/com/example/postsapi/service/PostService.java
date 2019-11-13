package com.example.postsapi.service;

import com.example.postsapi.model.Post;

public interface PostService {

    /*************************************************************************
     *       todo
     *      Comment for ProfileService
     *
     *************************************************************************/

    public Post createPost(Post post, String token);

    public Long deletePost(Long postId, String token);
}
