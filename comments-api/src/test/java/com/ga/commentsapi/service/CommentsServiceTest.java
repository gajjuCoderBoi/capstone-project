package com.ga.commentsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.bean.Post;
import com.ga.commentsapi.bean.User;
import com.ga.commentsapi.exception.CommentNotExistException;
import com.ga.commentsapi.exception.PostNotExistException;
import com.ga.commentsapi.exception.TokenException;
import com.ga.commentsapi.exception.UnauthorizeActionException;
import com.ga.commentsapi.messagequeue.Sender;
import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.repository.CommentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CommentsServiceTest {

    private static final String USERNAME = "user";
    private static final String TOKEN = "abcdef";


    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private Comment comment;

    @InjectMocks
    private User user;

    @InjectMocks
    private Post post;



    @InjectMocks
    private ObjectMapper objectMapper;


    @Mock
    Sender sender;

    @Before
    public void initDummies() {
        MockitoAnnotations.initMocks(this);
        user.setUsername(USERNAME);
        user.setUserId(1L);
        post.setTitle("Example post title");
        post.setText("Example post description is cool.");
        post.setPostId(1L);
        post.setUserId(1L);
        comment.setUser(user);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setId(1L);
        comment.setText("This is a simnple comment");
    }

    @Test
    public void createComment() throws TokenException, PostNotExistException {

        when(sender.getUserFromUserAPI(any())).thenReturn(user);
        when(sender.getPostByPostId(any())).thenReturn(post);
        when(commentRepository.save(comment)).thenReturn(comment);
        Comment actual = commentService.createComment(comment,1L, TOKEN);

        assertEquals(comment, actual);

    }

    @Test
    public void getCommentsbyPostId() {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        commentRepository.findCommentsbyPostId(post.getPostId());

        when(commentRepository.findCommentsbyPostId(any())).thenReturn((comments));
        List<Comment> savedComments = (List<Comment>) commentRepository.findCommentsbyPostId(post.getPostId());

        assertEquals(comments, savedComments);

    }


    @Test
    public void deleteCommentByCommentId() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(commentRepository.findById(any())).thenReturn(java.util.Optional.of(comment));
        Comment savedComment = commentRepository.findById(comment.getId()).orElse(null);

        if (savedComment.getId().longValue() != user.getUserId().longValue())
            throw new UnauthorizeActionException("Unauthorized Action.");
        Comment actual = savedComment;
        commentRepository.delete(savedComment);
        assertEquals(comment, actual);
    }

    @Test
    public void getCommentsByUser() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        commentRepository.findCommentsbyUserId(user.getUserId());

        when(commentRepository.findCommentsbyUserId(any())).thenReturn(comments);
        List<Comment> retrievedComments = commentService.getCommentsByUser(user);

        assertEquals(comments, retrievedComments);


    }

    @Test
    public void listComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        when(commentRepository.findAll()).thenReturn(comments);
         List<Comment> retrievedComments = commentService.listComments();

        assertEquals(comments, retrievedComments);
    }



}
