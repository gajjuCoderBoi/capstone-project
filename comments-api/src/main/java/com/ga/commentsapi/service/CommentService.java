package com.ga.commentsapi.service;


import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.model.User;

import java.util.List;

/*************************************************************************
 * The comment service interface sets up five methods that will be implemented by the
 *  the comment service implementation
 *
 *
 *************************************************************************/

public interface CommentService {

    public Comment createComment(Comment comment, Long postId, String token);

    public Iterable<Comment> getCommentsbyPostId(Long postId);

    public Long deleteCommentByCommentId(Long commentId, String token);

    public void getCommentsByUser(User user);

    public List<Comment> listComments();

}
