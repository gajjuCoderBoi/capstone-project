package com.ga.profileapi.messagequeue;

import com.ga.profileapi.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    private Sender sender;

    @Mock
    Queue queue;


    @Test
    public void getUserFromUserAPI() {
        when(queue.getName()).thenReturn("xyz");
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString())).thenReturn("null");

        User user = sender.getUserFromUserAPI("xyz");

        assertNull(user);
    }


}