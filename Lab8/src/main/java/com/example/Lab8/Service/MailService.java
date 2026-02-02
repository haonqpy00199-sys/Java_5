package com.example.Lab8.Service;

import lombok.Builder;
import lombok.Data;
import java.util.List;

public interface MailService {
    @Data
    @Builder
    public static class Mail {
        @Builder.Default
        private String from = "WebShop <web-shop@gmail.com>";
        private String to, cc, bcc, subject, body, filenames;
    }

    void send(Mail mail);
    void push(Mail mail);

    // Phương thức mới để lấy lịch sử
    List<Mail> getSentMails();

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder().to(to).subject(subject).body(body).build();
        this.send(mail);
    }

    default void push(String to, String subject, String body) {
        Mail mail = Mail.builder().to(to).subject(subject).body(body).build();
        this.push(mail);
    }
}