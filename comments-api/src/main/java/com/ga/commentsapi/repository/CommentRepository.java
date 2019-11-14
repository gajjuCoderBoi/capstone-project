package com.ga.commentsapi.repository;
import com.ga.commentsapi.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long > {
    /*************************************************************************
     * The CommentRepository handles all DB calls related to comments
     * it implements the CrudRepository class
     * Two Queries were set up. One to find comments by userId, the other
     * to find comments by postId
     *************************************************************************/

    @Query("FROM Comment c where c.userId=?1")
    public Iterable<Comment> findCommentsbyUserId(Long userId);

    @Query("FROM Comment c where c.postId=?1")
    public Iterable<Comment> findCommentsbyPostId(Long postId);

}