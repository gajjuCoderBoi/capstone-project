package com.ga.postsapi.controller;

import com.ga.postsapi.bean.PostRequestBody;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.service.PostService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
 *
 *     <p>This is the postcontroller class that defines the differnt endpoints used
 *      by this microservice</p>
 *
 *************************************************************************/

@RestController
@RequestMapping
@Api(tags = "Post Management System", produces = "application/json")
public class PostController {
    /**
     *  <p>postService is autowired. This is a necessary dependence so that we
     *  can call the services provided by PostService</p>
     *
     *************************************************************************/

    @Autowired
    PostService postService;


    /**
     *  <p>getPosts() is a method that takes no arguments. It will return a list of
     *  posts. It will accomplish this through a call to postServices function
     *  postList()</p>
     *
     *  @return It returns a list of posts
     *************************************************************************/
    @ApiOperation(value = "Return all the Posts exist in the Server", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    @GetMapping("/list")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.postList());
    }


    /**
     *  <p>getPostsById(Long postId) takes the PathVariable postId as an argument.
     *  It calls the postService.getPostById passing the postId. It will
     *  return the corresponding Post</p>
     *  @param postId  type Long
     *  @return one Post that owns the postId
     *************************************************************************/
    @ApiOperation(value = "Return the a Post by Post Id with all of it's Comments", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully Retrieved a Post", response = Post.class)
    })
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(
            @PathVariable @ApiParam(value = "Id of a Post", required = true) String postId
    ) {
        return ResponseEntity.ok(postService.getPostById(Long.valueOf(postId)));
    }


    /**
     *  <p>The createPost method takes two arguments: a post and a token.
     *   The function will in turn call the postService method createPost passing
     *  these two values which will in turn create the post</p>
     *  @param RequestBody that contains a post and a token
     *  @return a Post. The one that was created by this method
     *************************************************************************/
    @ApiOperation(value = "Create a Post.", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post Created Successfully", response = Post.class),
            @ApiResponse(code = 401, message = "Invalid Token")
    })
    @PostMapping
    public Post createPost(
            @Valid @RequestBody @ApiParam(
                    value = "Create Post Object.",
                    name = "Body",
                    required = true
            )
                    PostRequestBody Body,
            @RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token
    )
            throws TokenException {
        Post post = new Post();
        post.setText(Body.getText());
        post.setTitle(Body.getTitle());
        return postService.createPost(post, token);
    }


    /**
     *  <p>The deletePost method takes two parameters postId and token. The
     *  postService deletePost gets called receiving these two values and
     *  will further handle the deletePost request</p>
     *  @param postId a Pathvariable
     *  @return the postId for the post that gets deleted
     *************************************************************************/

    @ApiOperation("Delete a Post.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post Deleted Successfully", response = Long.class),
            @ApiResponse(code = 401, message = "Invalid Token."),
            @ApiResponse(code = 401, message = "Unauthorized Action."),
            @ApiResponse(code = 404, message = "Post doesn't Exist.")
    })
    @DeleteMapping("/{postId}")
    public Long deletePost(
            @PathVariable @ApiParam(value = "Post Id to delete Post.", required = true) String postId,
            @RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token

    ) throws UnauthorizeActionException, TokenException, PostNotExistException {
        return postService.deletePost(Long.valueOf(postId), token);
    }

    /**
     *  <p>getPosts() is a method that takes no arguments. It will return a list of
     *  posts. It will accomplish this through a call to postServices function
     *  postList()</p>
     *  @param token
     *  @return a list of posts created by the user that owns the token
     *************************************************************************/
    @ApiOperation(value = "Return all the Posts exist in the Server of a User", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "Invalid Token.")
    })
    @GetMapping("/user")
    public ResponseEntity<List<Post>> getPostsByUser(@RequestHeader("Authorization") @ApiParam(value = "Bearer Token:", required = true) String token) throws TokenException {

        return ResponseEntity.ok(postService.getPostsByUser(token));
    }




}
