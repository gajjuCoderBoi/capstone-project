package com.ga.commentsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.bean.User;
import com.ga.commentsapi.model.Comment;

import com.ga.commentsapi.repository.CommentRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private Queue commentToUser;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommentRepository commentRepository;
/*************************************************************************
 * The createComment method will create a comment. It takes three parameters:
 * a comment, a postId (for the post that is getting the comment) and a token
 * from which the comment will get the userid of the user who is posting the
 * comment. It uses the HttpHeaders to verify the token
 *
 *************************************************************************/
    @Override
    public Comment createComment(Comment comment, Long postId, String token){

//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setBearerAuth(token.substring(7));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();

        User user = getUserFromUserAPI(token);
        if (user==null){
            return null;
        }

        comment.setUserId(user.getUserId());
        comment.setPostId(postId);
        comment.setUser(user);
        return commentRepository.save(comment);
    };

    /*************************************************************************
     * The getCommentsbyPostId takes a single parameter postId and calls
     *  commentRepository.findCommentsbyPostId to get all the comments
     *  for the post whose id is being passed to the function
     *  It gets the list of all comments and extracts the list of userid's
     *  The it puts userid's in the list of all saved comments
     ************************************************************************
     * @return*/

    @Override
    public Iterable<Comment> getCommentsbyPostId(Long postId){

        //return commentRepository.findCommentsbyPostId(postId);

        List<Comment> savedComments = (List<Comment>) commentRepository.findCommentsbyPostId(postId);
        Set<Long> userIdList = savedComments.stream().map(Comment::getUserId).collect(Collectors.toSet());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Set<Long>> requestEntity = new HttpEntity<Set<Long>>(userIdList,headers);
//        User[] rateResponse = restTemplate.exchange("http://users-api:5001/userlist", HttpMethod.POST, requestEntity,User[].class).getBody();
        String message = "";
        User[] rateResponse = null;
        try {
            message = objectMapper.writeValueAsString(userIdList);

            String response = (String) rabbitTemplate.convertSendAndReceive(commentToUser.getName(), "usersList:"+message);

            rateResponse = objectMapper.readValue(response, User[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Long, User> userHashMap = new LinkedHashMap<>();
        for (User user:rateResponse){
            userHashMap.put(user.getUserId(), user);
        }
        for (Comment savedComment : savedComments) {
            savedComment.setUser(userHashMap.get(savedComment.getUserId()));
        }
        System.out.println(rateResponse);
        return savedComments;
    };

    /*************************************************************************
     * The deleteCommentByCommentId gets two arguments: a commentId (commentId
     * belongs to the comment that will be deleted and a token to validate
     * the authority of the user who is trying to delete
     ************************************************************************
     * @return*/

    @Override
    public Long deleteCommentByCommentId(Long commentId, String token){
        Comment savedComment = commentRepository.findById(commentId).orElse(null);
        User user = getUserFromUserAPI(token);
        if(user.getUserId().equals(savedComment.getUserId())) {
            commentRepository.deleteById(commentId);
            return savedComment.getId();
        }
        return -1L;
    };

    /*************************************************************************
     * The getCommentsByUser method takes one argument: a User. From the user
     * the userid is extracted to call commentRepository.findCommentsbyUserId(userid)
     * to find all the comments that are related to this particular user
     *************************************************************************/

    @Override
    public void getCommentsByUser(User user){
        Long userid = user.getUserId();
        commentRepository.findCommentsbyUserId(userid);

    };

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

    private User getUserFromUserAPI(String token){
//
        String response = (String) rabbitTemplate.convertSendAndReceive(commentToUser.getName(), "getUserByToken:"+token);
        User user=null;
        try {
            user =  objectMapper.readValue(response,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
