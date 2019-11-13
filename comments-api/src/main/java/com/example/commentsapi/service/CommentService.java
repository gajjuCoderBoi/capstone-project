package com.example.commentsapi.service;

import com.example.commentsapi.model.Comment;
import com.example.commentsapi.model.User;

public interface CommentService {

    public Comment createComment(Comment comment, String token);

    public void getCommentsbyPostId(Long PostId);

    public void deleteCommentByCommentId(Long CommentId, String token);

    public void getCommentsByUser(User user);

}
