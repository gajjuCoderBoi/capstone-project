package com.ga.commentsapi.service;


import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.model.User;


public interface CommentService {

    public Comment createComment(Comment comment, String token);

    public void getCommentsbyPostId(Long PostId);

    public void deleteCommentByCommentId(Long CommentId, String token);

    public void getCommentsByUser(User user);

}
