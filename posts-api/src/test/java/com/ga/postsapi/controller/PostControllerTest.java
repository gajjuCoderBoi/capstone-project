package com.ga.postsapi.controller;

import com.ga.postsapi.bean.User;
import com.ga.postsapi.exception.PostNotExistException;
import com.ga.postsapi.exception.TokenException;
import com.ga.postsapi.exception.UnauthorizeActionException;
import com.ga.postsapi.model.Post;
import com.ga.postsapi.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @InjectMocks
    private Post post;

    @InjectMocks
    private User user;

    private List<Post> posts;

    @Before
    public void initDummies(){
        user.setUsername("batman");
        user.setUserId(1L);
        post.setTitle("Example post title");
        post.setText("Example post description is cool.");
        post.setPostId(1L);
        post.setUser(user);
        post.setUserId(1L);
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
        posts = Arrays.asList(post1, post2, post3);
//        User user1 = new User();
//        user1.setUserId(3L);
//        User user2 = new User();
//        user2.setUserId(4L);
//        User[] users = {user1, user2};
    }

    @Test
    public void getPosts_Posts_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list")
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.postList()).thenReturn(posts);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void getPostById_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/1")
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.getPostById(anyLong())).thenReturn(post);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createPost_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, "xyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPostJson(post.getTitle(), post.getText()))
                ;

        when(postService.createPost(any(), anyString())).thenReturn(post);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createPost_Unauthorized_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPostJson(post.getTitle(), post.getText()))
                ;


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void createPost_MissingAttribute_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, "xyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content("sadfsaf")
                ;


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void deletePost_PostId_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header(HttpHeaders.AUTHORIZATION, "xyz")
                ;

        when(postService.deletePost(anyLong(), anyString())).thenReturn(post.getPostId());

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deletePost_Unauthorized_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                ;


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    private String createPostJson(String title, String body){
        return "{" +
                "\"title\":\"" + title + "\", " +
                "\"text\":\"" + body + "\" " +
                "}";
    }
}