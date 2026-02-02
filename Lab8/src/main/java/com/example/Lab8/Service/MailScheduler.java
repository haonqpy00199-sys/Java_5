package com.example.Lab8.Service;

import com.example.Lab8.ServiceImpl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailScheduler {

    @Autowired
    MailServiceImpl mailService;

    // fixedRate = 10000ms = 10 giây
    @Scheduled(fixedRate = 10000)
    public void sendAutomaticMail() {
        try {
            String to = "nguyenquochao1101@gmail.com"; // Thay bằng email nhận thật để test
            String subject = "Hệ thống gửi tự động";
            String body = "Đây là mail gửi tự động mỗi 10 giây để hoàn thành Lab 8.";

            mailService.send(to, subject, body);

            System.out.println("===> [AUTO] Đã gửi mail thành công lúc: " + new java.util.Date());
        } catch (Exception e) {
            System.err.println("===> [ERROR] Gửi mail tự động thất bại: " + e.getMessage());
        }
    }
}