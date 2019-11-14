package com.ga.commentsapi.service;

import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.model.User;

import com.ga.commentsapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();

        if (user==null){
            return null;
        }
        comment.setUserId(user.getUserId());
        comment.setCommentId(postId);
        return commentRepository.save(comment);
    };

    /*************************************************************************
     * The getCommentsbyPostId takes a single parameter postId and calls
     *  commentRepository.findCommentsbyPostId to get all the comments
     *  for the post whose id is being passed to the function
     *
     *************************************************************************/

    @Override
    public void getCommentsbyPostId(Long postId){
        commentRepository.findCommentsbyPostId(postId);
    };

    /*************************************************************************
     * The deleteCommentByCommentId gets two arguments: a commentId (commentId
     * belongs to the comment that will be deleted and a token to validate
     * the authority of the user who is trying to delete
     *************************************************************************/

    @Override
    public void deleteCommentByCommentId(Long commentId, String token){
        commentRepository.deleteById(commentId);

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
     *
     *
     *
     */

    @Override
    public List<Comment> listComments() {
        return (List<Comment>) commentRepository.findAll();
    }
}
