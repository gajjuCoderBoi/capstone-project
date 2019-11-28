package com.ga.commentsapi.service;


import com.ga.commentsapi.exception.CommentNotExistException;
import com.ga.commentsapi.exception.PostNotExistException;
import com.ga.commentsapi.exception.TokenException;
import com.ga.commentsapi.exception.UnauthorizeActionException;
import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.bean.User;

import java.util.List;

/*************************************************************************
 * The comment service interface sets up five methods that will be implemented by the
 *  the comment service implementation
 *
 *
 *************************************************************************/

public interface CommentService {

    Comment createComment(Comment comment, Long postId, String token) throws TokenException, PostNotExistException;

    Iterable<Comment> getCommentsbyPostId(Long postId);

    Long deleteCommentByCommentId(Long commentId, String token) throws TokenException, UnauthorizeActionException, CommentNotExistException;

    List<Comment>  getCommentsByUser(User user);

    List<Comment> listComments();

    Long deleteCommentsByPostId(Long postId);
}
