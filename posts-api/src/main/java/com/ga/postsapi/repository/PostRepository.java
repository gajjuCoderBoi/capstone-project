package com.ga.postsapi.repository;

import com.ga.postsapi.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/*************************************************************************
 * The PostRepository handles all DB calls related to posts
 * it implements the CrudRepository class
 * A special Query was set up to find posts by userId
 *
 *************************************************************************/


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @Query("FROM Post p where p.userId=?1")
    public Post getPostByUserId(Long userId);

    @Query("FROM Post p where p.userId=?1")
    public Post getPostsByUserId(Long userId);
}
