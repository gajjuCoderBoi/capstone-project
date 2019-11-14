package com.ga.postsapi.service;


import com.ga.postsapi.model.Post;

import java.util.List;

public interface PostService {

    /*************************************************************************
     *       todo
     *      Comment for ProfileService
     *
     *************************************************************************/

    public Post createPost(Post post, String token);

    public Long deletePost(Long postId, String token);

    public List<Post> postList();

    public Post getPostById(Long postId);


}
