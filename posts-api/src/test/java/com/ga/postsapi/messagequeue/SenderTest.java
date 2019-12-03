package com.ga.postsapi.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postsapi.bean.Comment;
import com.ga.postsapi.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {


    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    private Sender sender;


    @Mock
    private Queue postToComment;

    @Mock
    private Queue postToUser;

    @Mock
    ObjectMapper objectMapper;


    @Test
    public void getUserFromUserAPI() {
        when(postToUser.getName()).thenReturn("xyz");
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString())).thenReturn("null");

        User user = sender.getUserFromUserAPI("xyz");

        assertNull(user);
    }

    @Test
    public void deleteCommentsOfPost() {
        when(postToComment.getName()).thenReturn("xyz");
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString())).thenReturn("1");

        Long actual = sender.deleteCommentsOfPost(1l);

        assertEquals(java.util.Optional.of(1L), java.util.Optional.of(actual));
    }

    @Test
    public void getUsersByUserId() throws IOException {
        //String json = "["+createUserInJson("batman@email.com", "batman", "1")+"]";
        String json = "[{\"userId\":1,\"email\":\"batman@email.com\",\"password\":\"$2a$10$bUnzsJlnt5.MHfUaZuA63OAqS9SCkbWkq26nKPbN9YwMkLFQncWv.\",\"username\":\"batman\"},{\"userId\":2,\"email\":\"superman@email.com\",\"password\":\"$2a$10$WU5W49PxLmS11j8e7lrM2ugBudUaU3VYOmcEhHtiezxEly9bEA6C2\",\"username\":\"superman\"}]";
        Set<Long> userId = new LinkedHashSet<>();
        userId.add(1L);
        userId.add(2L);
        when(postToUser.getName()).thenReturn("xyz");
        when(objectMapper.writeValueAsString(any())).thenReturn("xyz");
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString())).thenReturn(json);

        User[] users = sender.getUsersByUserId(userId);


    }

    @Test
    public void findCommentsByPostId() throws IOException {
        when(postToComment.getName()).thenReturn("xyz");
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString())).thenReturn("xyz");

        Comment[] comments = sender.findCommentsByPostId(1L);


    }

    private static String createUserInJson(String email, String username, String userId) {
        return "{ " +
                "\"username\": \"" + username + "\", " +
                " \"email\": \""+ email + "\", " +
                "\"userId\":\"" + userId + "\"" +
                "}";
    }
}