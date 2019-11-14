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

    @PostMapping("/{postId}")
    public Comment createComment(@RequestBody Comment comment, @PathVariable Long postId, @RequestHeader("Authorization") String token){
        return commentService.createComment(comment, postId, token);
    }

    @GetMapping("/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId){
        return commentService.getCommentsbyPostId(postId);
    }
}
