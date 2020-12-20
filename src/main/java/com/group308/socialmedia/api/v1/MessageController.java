package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.MessageDto;
import com.group308.socialmedia.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageDto message) {
        System.out.println("handling send message: " + message + " to: " + to);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }
}

