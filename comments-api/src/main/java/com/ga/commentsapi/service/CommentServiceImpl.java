package com.ga.commentsapi.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.bean.Post;
import com.ga.commentsapi.bean.User;
import com.ga.commentsapi.exception.CommentNotExistException;
import com.ga.commentsapi.exception.PostNotExistException;
import com.ga.commentsapi.exception.TokenException;
import com.ga.commentsapi.exception.UnauthorizeActionException;
import com.ga.commentsapi.model.Comment;

import com.ga.commentsapi.repository.CommentRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    /*************************************************************************
     *
     *      Autowiring the RestTemplate, CommentRepository
     *      These are the dependencies for the Comment services and used in
     *      this service
     *
     *************************************************************************/
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @Qualifier("CommentToUser")
    private Queue commentToUserQueue;

    @Autowired
    @Qualifier("CommentToPost")
    private Queue commentToPostQueue;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommentRepository commentRepository;

    /*************************************************************************
     * The createComment method will create a comment. It takes three parameters:
     * a comment, a postId (for the post that is getting the comment) and a token
     * from which the comment will get the userid of the user who is posting the
     * comment. It uses the HttpHeaders to verify the token
     *
     *************************************************************************/
    @Override
    public Comment createComment(Comment comment, Long postId, String token) throws TokenException, PostNotExistException {


        User user = getUserFromUserAPI(token);
        if (user == null) throw new TokenException("Invalid Token.");

        String response = (String) rabbitTemplate.convertSendAndReceive(commentToPostQueue.getName(), "getPostByPostId:" + postId);
        Post post = null;

        try {
            post = response.equals("null") ? null : objectMapper.readValue(response, Post.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (post == null) throw new PostNotExistException("Post Doesn't Exist!");

        comment.setUserId(user.getUserId());
        comment.setPostId(postId);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    ;

    /*************************************************************************
     * The getCommentsbyPostId takes a single parameter postId and calls
     *  commentRepository.findCommentsbyPostId to get all the comments
     *  for the post whose id is being passed to the function
     *  It gets the list of all comments and extracts the list of userid's
     *  The it puts userid's in the list of all saved comments
     ************************************************************************
     * @return*/

    @Override
    public Iterable<Comment> getCommentsbyPostId(Long postId) {

        //return commentRepository.findCommentsbyPostId(postId);

        List<Comment> savedComments = (List<Comment>) commentRepository.findCommentsbyPostId(postId);
        if (savedComments.size() > 0) {
            Set<Long> userIdList = savedComments.stream().map(Comment::getUserId).collect(Collectors.toSet());
            String message = "";
            User[] rateResponse = null;
            try {
                message = objectMapper.writeValueAsString(userIdList);

                String response = (String) rabbitTemplate.convertSendAndReceive(commentToUserQueue.getName(), "usersList:" + message);

                rateResponse = objectMapper.readValue(response, User[].class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            HashMap<Long, User> userHashMap = new LinkedHashMap<>();
            for (User user : rateResponse) {
                userHashMap.put(user.getUserId(), user);
            }
            for (Comment savedComment : savedComments) {
                savedComment.setUser(userHashMap.get(savedComment.getUserId()));
            }
            System.out.println(rateResponse);
        }
        return savedComments;
    }

    /*************************************************************************
     * The deleteCommentByCommentId gets two arguments: a commentId (commentId
     * belongs to the comment that will be deleted and a token to validate
     * the authority of the user who is trying to delete
     ************************************************************************
     * @return*/

    @Override
    public Long deleteCommentByCommentId(Long commentId, String token) throws TokenException, UnauthorizeActionException, CommentNotExistException {
        Comment savedComment = commentRepository.findById(commentId).orElse(null);
        if(savedComment==null) throw new CommentNotExistException("Comment Doesn't Exist.");
        User user = getUserFromUserAPI(token);
        if (user == null) throw new TokenException("Invalid Token.");
        if (user.getUserId().equals(savedComment.getUserId())) {
            commentRepository.deleteById(commentId);
            return savedComment.getId();
        } else if (!user.getUserId().equals(savedComment.getUserId())) {
            throw new UnauthorizeActionException("Unauthorized Action.");
        }
        return -1L;
    }

    ;

    /*************************************************************************
     * The getCommentsByUser method takes one argument: a User. From the user
     * the userid is extracted to call commentRepository.findCommentsbyUserId(userid)
     * to find all the comments that are related to this particular user
     *************************************************************************/

    @Override
    public void getCommentsByUser(User user) {
        Long userid = user.getUserId();
        commentRepository.findCommentsbyUserId(userid);
    }

    /*************************************************************************
     * the listComments will bring all comments from the repository
     * It does not need a parameter. It calls the commentRepository.findAll()
     * method
     *************************************************************************/

    @Override
    public List<Comment> listComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    /*************************************************************************
     * the deleteCommentsByPostId takes postId as a single argument.
     * It first gets a list of the comments attached to the particular PostId
     * and then it calls the deleteAll method of the commentRepository passing
     * the list of comments
     *************************************************************************/

    @Override
    public Long deleteCommentsByPostId(Long postId) {
        List<Comment> savedComments = (List<Comment>) commentRepository.findCommentsbyPostId(postId);
        commentRepository.deleteAll(savedComments);
        return 1L;
    }

    /*************************************************************************
     * the getUserFromUserAPI takes a token as argument. It is an auxiliary
     * method that returns the user that owns the token
     *************************************************************************/

    private User getUserFromUserAPI(String token) {
        String response = (String) rabbitTemplate.convertSendAndReceive(commentToUserQueue.getName(), "getUserByToken:" + token);
        User user = null;
        try {
            user = response.equals("null") ? null :  objectMapper.readValue(response,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
