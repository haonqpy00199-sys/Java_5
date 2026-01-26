package com.example.Lab8.Service;

import lombok.Builder;
import lombok.Data;

public interface MailService {
    @Data
    @Builder
    public static class Mail {
        @Builder.Default
        private String from = "WebShop <web-shop@gmail.com>";
        private String to, cc, bcc, subject, body, filenames;
    }

    void send(Mail mail);

    // Bài 2: Khai báo push để xếp vào hàng đợi
    void push(Mail mail);

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder().to(to).subject(subject).body(body).build();
        this.send(mail);
    }

    // Bài 2: Phương thức mặc định để push nhanh
    default void push(String to, String subject, String body) {
        Mail mail = Mail.builder().to(to).subject(subject).body(body).build();
        this.push(mail);
    }


}