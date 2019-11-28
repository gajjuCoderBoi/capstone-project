package com.ga.postsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.messagequeue.Sender;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
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
public class PostServiceTest {

    private static final String USERNAME = "user";
    private static final String TOKEN = "abcdef";

//    @Spy
//    private PostServiceImpl postService;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private Post post;

    @InjectMocks
    private User user;


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
        post.setUser(user);
        post.setUserId(1L);
    }

    @Test
    public void createPost() throws TokenException {
        when(sender.getUserFromUserAPI(any())).thenReturn(user);
        when(postRepository.save(post)).thenReturn(post);
        Post actual = postService.createPost(post, TOKEN);

        assertEquals(post, actual);

    }

    @Test
    public void deletePost() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(postRepository.findById(any())).thenReturn(java.util.Optional.of(post));
        Post savedPost = postRepository.findById(post.getPostId()).orElse(null);
        if (savedPost == null) throw new PostNotExistException("Post Doesn't Exist.");
        if (savedPost.getUserId().longValue() != user.getUserId().longValue())
            throw new UnauthorizeActionException("Unauthorized Action.");
        sender.deleteCommentsOfPost(savedPost.getPostId());
        Post actual = savedPost;
        postRepository.delete(savedPost);
        assertEquals(post, actual);
    }

    @Test
    public void postList() {
        Post post1 = new Post();
        List<Post> addedPosts = new ArrayList<Post>();
        post1.setTitle("Example post1 title");
        post1.setText("Example post1 description is cool.");
        post1.setPostId(2L);
        post1.setUser(user);
        post1.setUserId(1L);
        Post post2 = new Post();
        post2.setTitle("Example post2 title");
        post2.setText("Example post2 description is cool.");
        post2.setPostId(3L);
        post2.setUser(user);
        post2.setUserId(1L);
        Post post3 = new Post();
        post3.setTitle("Example post3 title");
        post3.setText("Example post3 description is cool.");
        post3.setPostId(4L);
        post3.setUser(user);
        post3.setUserId(1L);
        addedPosts.add(post1);
        addedPosts.add(post2);
        addedPosts.add(post3);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> actualPosts = addedPosts;

        assertEquals(addedPosts, actualPosts);
    }

}