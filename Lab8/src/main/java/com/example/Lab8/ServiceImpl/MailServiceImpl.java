package com.example.Lab8.ServiceImpl;

import com.example.Lab8.Service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("mailService")
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender mailSender;

    // Hàng đợi chứa các mail chờ gửi
    private List<MailService.Mail> queue = new ArrayList<>();

    // Danh sách lưu lịch sử các mail đã gửi thành công
    private List<MailService.Mail> sentMails = new ArrayList<>();

    @Override
    public void send(MailService.Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());
            if (mail.getCc() != null && !mail.getCc().isEmpty()) helper.setCc(mail.getCc());
            if (mail.getBcc() != null && !mail.getBcc().isEmpty()) helper.setBcc(mail.getBcc());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            String filenames = mail.getFilenames();
            if (filenames != null && !filenames.isEmpty()) {
                for (String filename : filenames.split("[,;]+")) {
                    File file = new File(filename.trim());
                    if (file.exists()) {
                        helper.addAttachment(file.getName(), file);
                    }
                }
            }

            mailSender.send(message);
            sentMails.add(mail); // Lưu vào lịch sử
            System.out.println("==> Đã gửi thành công đến: " + mail.getTo());

        } catch (Exception e) {
            System.err.println("Lỗi gửi mail: " + e.getMessage());
        }
    }

    @Override
    public void push(MailService.Mail mail) {
        queue.add(mail);
    }

    @Override
    public List<MailService.Mail> getSentMails() {
        return sentMails;
    }

    /**
     * TÍNH NĂNG TEST: Tự động tạo mail mỗi 10 giây và đẩy vào hàng đợi
     * Chú ý: Thay địa chỉ email nhận để test
     */
    @Scheduled(fixedRate = 10000)
    public void autoGenerateSpam() {
        MailService.Mail spamMail = MailService.Mail.builder()
                .to("email-nhan-test@gmail.com") // <== THAY EMAIL CỦA BẠN VÀO ĐÂY
                .subject("Spam Test - " + System.currentTimeMillis())
                .body("Đây là nội dung gửi tự động mỗi 10 giây để kiểm tra hệ thống.")
                .build();

        this.push(spamMail);
        System.out.println(">>> Đã đẩy 1 mail spam mới vào hàng đợi.");
    }

    /**
     * Quét hàng đợi để gửi mail.
     * Để gửi đúng nhịp 10s/lần, ta lấy từng mail ra xử lý.
     */
    @Scheduled(fixedDelay = 10000)
    public void run() {
        if (!queue.isEmpty()) {
            MailService.Mail mail = queue.remove(0);
            this.send(mail);
        }
    }
}