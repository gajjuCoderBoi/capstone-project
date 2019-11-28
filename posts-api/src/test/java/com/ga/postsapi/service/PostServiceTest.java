package com.ga.postsapi.service;

import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.messagequeue.Sender;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    private static final String USERNAME = "user";
    private static final String TOKEN = "abcdef";

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private Post post;

    @InjectMocks
    private User user;


    @Mock
    Sender sender;

    @Before
    public void initDummies() {
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
}