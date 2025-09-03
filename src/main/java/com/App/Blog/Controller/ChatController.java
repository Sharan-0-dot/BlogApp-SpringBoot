package com.App.Blog.Controller;

import com.App.Blog.Model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/public")  // clients send to /app/public
    public void publicMessage(ChatMessage message, Principal principal) {
        String sender = principal != null ? principal.getName() : message.getFrom();
        message.setFrom(sender);
        message.setTimestamp(System.currentTimeMillis());
        simpMessagingTemplate.convertAndSend("/topic/public", message);
    }
}
