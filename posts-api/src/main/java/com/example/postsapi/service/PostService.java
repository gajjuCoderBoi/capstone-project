package com.example.postsapi.service;

import com.example.postsapi.model.Post;

import java.util.List;

public interface PostService {

    /*************************************************************************
     *
     *  The ProfileService has three services which will be implemented
     *  in the ProfileServiceImpl class
     *************************************************************************/

    public Post createPost(Post post, String token);

    public Long deletePost(Long postId, String token);

    public List<Post> listPosts();
}
