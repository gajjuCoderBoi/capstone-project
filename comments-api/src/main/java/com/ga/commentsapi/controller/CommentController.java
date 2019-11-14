package com.ga.commentsapi.controller;

import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class CommentController {

    /*************************************************************************
     *
     *      Autowiring the profileService that will be used to get the data
     *      from the service layer.
     *
     *************************************************************************/

    @Autowired
    CommentService commentService;

    /*************************************************************************
     *
     *      The method createComment takes three arguments: an instance of Comment,
     *      the Long pathvariable postId and a token. It calls createComment
     *      from the service layer so that a comment will be created and saved in the repository
     *
     *************************************************************************/

    @PostMapping("/{postId}")
    public Comment createComment(@RequestBody Comment comment, @PathVariable Long postId, @RequestHeader("Authorization") String token){
        return commentService.createComment(comment, postId, token);
    }

    /*************************************************************************
     *
     *      This method takes the path variable postId as an argument and then
     *      calls commentService layer to get the comments by postID
     *
     *************************************************************************/

    @GetMapping("/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId){
        return commentService.getCommentsbyPostId(postId);
    }

    /*************************************************************************
     *
     *      This method takes the path variable commentId as an argument
     *      plus the token from the Authorization header and then
     *      calls commentService layer to delete the comments by commentID
     *
     *************************************************************************/

    @DeleteMapping("/{commentId}")
    public Long deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String token){
        return commentService.deleteCommentByCommentId(commentId, token);
    }
}
