package com.example.Assignment.controller;

import com.example.Assignment.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        // Gửi tới người nhận tại đường dẫn: /user/{toUser}/queue/messages
        messagingTemplate.convertAndSendToUser(
                chatMessage.getTo(), // Tên đăng nhập của người nhận
                "/queue/messages",
                chatMessage
        );
    }
}
