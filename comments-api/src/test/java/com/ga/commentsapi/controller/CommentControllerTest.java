package com.ga.commentsapi.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentsapi.bean.Post;
import com.ga.commentsapi.bean.User;
import com.ga.commentsapi.model.Comment;

import com.ga.commentsapi.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CommentControllerTest {

    private MockMvc mockMvc;
    private static final String TEXT = "i dont like this";
    private static final Long POST_ID = 3L;
    private String  token = "abscc";

    @InjectMocks
    CommentController commentController;

    @InjectMocks
    User user;

    @InjectMocks
    Post post;

    @InjectMocks
    Comment comment;

    @InjectMocks
    ObjectMapper objectMapper;

    @Mock
    CommentService commentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        user.setUsername("downer");
        post.setPostId(POST_ID);
        comment.setId(1L);
        comment.setText(TEXT);
        comment.setPostId(post.getPostId());
        comment.setUser(user);
        comment.setId(1L);
    }

    @Test
    public void createComment_validCommentWithAuth_Success() throws Exception {
        String jsonComment = createCommentInJson(TEXT);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/3")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCommentInJson(comment.getText()));

        when(commentService.createComment(any(), anyLong(), anyString())).thenReturn(comment);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comment)));
    }

    @Test
    public void createComment_InvalidCommentFormatBlankText_Failure() throws Exception {
        String jsonComment = createCommentInJson("");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/3")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonComment);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verify(commentService, never()).createComment(any(), anyLong(),anyString());
    }

    @Test
    public void createComment_InvalidCommentFormatNullText_Failure() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/3")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verify(commentService, never()).createComment(any(), anyLong(),anyString());
    }

    @Test
    public void deleteComment_SuccessfulDeletion_Success() throws Exception {
        String commentId = "1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("Authorization", token);

        when(commentService.deleteCommentByCommentId(anyLong(),anyString())).thenReturn(comment.getId());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(commentService, times(1)).deleteCommentByCommentId(anyLong(),anyString());
    }


    private static String createCommentInJson(String text) {
        return "{ \"text\": \"" + text + "\" }";
    }
}