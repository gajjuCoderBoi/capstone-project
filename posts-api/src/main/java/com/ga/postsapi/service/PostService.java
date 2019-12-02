package com.ga.postsapi.service;


import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.model.Post;

import java.io.IOException;
import java.util.List;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/


public interface PostService {

    /**
     *
     *    <p> All methods are implemented into PostServiceImpl class
     *
     *      createPost method to Create the Post.
     *      deletePost method to delete the Post.
     *      postList method is to get All the Post exist into the database.
     *      getPostById method to fetch the specfic Post with it's Comments.
     *      </p>
     *************************************************************************/

    public Post createPost(Post post, String token) throws TokenException;

    public Long deletePost(Long postId, String token) throws TokenException, UnauthorizeActionException, PostNotExistException;

    public List<Post> postList();

    public Post getPostById(Long postId);

    public List<Post> getPostsByUser(String token) throws TokenException;



}
