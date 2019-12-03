package com.ga.commentsapi.controller;

import com.ga.commentsapi.exception.CommentNotExistException;
import com.ga.commentsapi.exception.PostNotExistException;
import com.ga.commentsapi.exception.TokenException;
import com.ga.commentsapi.exception.UnauthorizeActionException;
import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping
@Api(tags = "Comment Management System", produces = "application/json")
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
    @ApiOperation(value = "Creates a comment attached to a Post Id", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Created a comments attached to a PostId", response = Comment.class)
    })
    @PostMapping("/{postId}")
    public Comment createComment(@Valid @RequestBody Comment comment, @PathVariable Long postId, @RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token) throws TokenException, PostNotExistException {
        return commentService.createComment(comment, postId, token);
    }

    /*************************************************************************
     *
     *      This method takes the path variable postId as an argument and then
     *      calls commentService layer to get the comments by postID
     *
     *************************************************************************/
    @ApiIgnore
     @ApiOperation(value = "Return the a list of comments by Post Id", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Retrieved comments by PostId", response = Comment.class)
    })
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
    @ApiOperation(value = "Deletes a comment by Comment Id", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Deleted comments by CommentId", response = Comment.class)
    })
    @DeleteMapping("/{commentId}")
    public Long deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token) throws UnauthorizeActionException, TokenException, CommentNotExistException {
        return commentService.deleteCommentByCommentId(commentId, token);
    }

    /*************************************************************************
     *
     *      Delete Comments of the Post. That takes the postId as input and
     *      Call the deleteCommentsByPostId from commentService.
     *
     *************************************************************************/
    /*@ApiIgnore
    *//*@ApiOperation(value = "Deletes comments related to a Post by Post Id", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Deleted comments by PostId", response = Comment.class)
    })*//*
    @DeleteMapping("/{postId}/comments")
    public Long deleteCommentsByPostId(@PathVariable Long postId){
        return commentService.deleteCommentsByPostId(postId);
    }*/

    @ApiOperation(value = "List Comments by UserId", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Returned Comment List.", response = Comment.class)
    })
    @GetMapping("/user")
    public List<Comment> getCommentsByUser(@RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token) throws TokenException {
        return commentService.getCommentsByUserId(token);
    }
}
