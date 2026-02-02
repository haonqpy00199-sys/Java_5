package com.example.Assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự động tạo getTo(), setTo(), getSender(),...
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String sender;  // Tên người gửi
    private String to;      // Tên người nhận (Recipient) - CẦN THÊM DÒNG NÀY
    private String content; // Nội dung tin nhắn
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}