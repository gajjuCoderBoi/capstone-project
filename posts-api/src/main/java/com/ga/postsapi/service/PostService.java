package com.ga.postsapi.service;


import com.ga.postsapi.model.Post;

import java.util.List;

public interface PostService {

    /*************************************************************************
     *
     *       All methods are implemented into PostServiceImpl class
     *
     *      createPost method to Create the Post.
     *      deletePost method to delete the Post.
     *      postList method is to get All the Post exist into the database.
     *      getPostById method to fetch the specfic Post with it's Comments.
     *
     *************************************************************************/

    public Post createPost(Post post, String token);

    public Long deletePost(Long postId, String token);

    public List<Post> postList();

    public Post getPostById(Long postId);


}
