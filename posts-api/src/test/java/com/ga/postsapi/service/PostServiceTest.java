package com.ga.postsapi.service;

import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.messagequeue.Sender;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    private static final String USERNAME = "user";
    private static final String TOKEN = "abcdef";

    @InjectMocks
    private PostServiceImpl postService;

    @Spy
    private PostRepository postRepository;

    @InjectMocks
    private Post post;

    @InjectMocks
    private User user;


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
    public void createPost_Post_Success() throws TokenException {
        when(sender.getUserFromUserAPI(any())).thenReturn(user);
        when(postRepository.save(post)).thenReturn(post);
        Post actual = postService.createPost(post, TOKEN);

        assertEquals(post.getTitle(), actual.getTitle());

    }

    @Test(expected = TokenException.class)
    public void createPost_Unauthorized_Error() throws TokenException {
        when(sender.getUserFromUserAPI(any())).thenReturn(null);
        Post actual = postService.createPost(post, TOKEN);

        assertEquals(post.getTitle(), actual.getTitle());

    }

    @Test
    public void deletePost_Long_Success() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(sender.getUserFromUserAPI(anyString())).thenReturn(user);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(sender.deleteCommentsOfPost(anyLong())).thenReturn(1L);
        doNothing().when(postRepository).delete(any());

        Long actual = postService.deletePost(1L, "xyz");

        assertEquals(Optional.of(1L), Optional.of(actual));

    }

    @Test(expected = TokenException.class)
    public void deletePost_TokenException_Error() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(sender.getUserFromUserAPI(anyString())).thenReturn(null);

        Long actual = postService.deletePost(1L, "xyz");

        assertEquals(Optional.of(1L), Optional.of(actual));

    }

    @Test(expected = Exception.class)
    public void deletePost_PostNotExist_Error() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(sender.getUserFromUserAPI(anyString())).thenReturn(user);
        when(postRepository.findById(anyLong())).thenReturn(null);

        Long actual = postService.deletePost(1L, "xyz");

        assertEquals(Optional.of(1L), Optional.of(actual));

    }

    @Test(expected = UnauthorizeActionException.class)
    public void deletePost_UnauthorizedAction_Error() throws TokenException, UnauthorizeActionException, PostNotExistException {

        User user = new User();
        user.setUserId(3L);
        when(sender.getUserFromUserAPI(anyString())).thenReturn(user);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Long actual = postService.deletePost(1L, "xyz");

        assertEquals(Optional.of(1L), Optional.of(actual));

    }

    @Test
    public void getPostById_Post_Success() {
        Comment[] comments = new Comment[1];
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(sender.findCommentsByPostId(anyLong())).thenReturn(comments);

        Post actual = postService.getPostById(1L);

        assertEquals(post.getTitle(), actual.getTitle());


    }


    @Test
    public void postList_Posts_Success() {
        Post post1 = new Post();
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
        List<Post> posts = Arrays.asList(post1, post2, post3);
        User user1 = new User();
        user1.setUserId(3L);
        User user2 = new User();
        user2.setUserId(4L);
        User[] users = {user1, user2};

        when(postRepository.findAll()).thenReturn(posts);
        when(sender.getUsersByUserId(anySet())).thenReturn(users);

        List<Post> actual = postService.postList();

        assertEquals(posts.size(), actual.size());

    }

}