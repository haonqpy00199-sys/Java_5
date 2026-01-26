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

    // Khởi tạo hàng đợi để tránh lỗi NullPointerException
    private List<MailService.Mail> queue = new ArrayList<>();

    @Override
    public void send(MailService.Mail mail) { // Sửa lại kiểu dữ liệu Mail từ Interface
        try {
            // 1. Tạo MimeMessage [cite: 55]
            MimeMessage message = mailSender.createMimeMessage();

            // 2. Tạo đối tượng hỗ trợ soạn thảo [cite: 57, 58]
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            // 2.1. Ghi thông tin người gửi và người nhận [cite: 80, 81, 83]
            helper.setFrom(mail.getFrom());
            helper.setReplyTo(mail.getFrom());
            helper.setTo(mail.getTo());

            // 2.2. Xử lý CC và BCC [cite: 84, 86, 87, 89]
            if (!this.isNullOrEmpty(mail.getCc())) {
                helper.setCc(mail.getCc());
            }
            if (!this.isNullOrEmpty(mail.getBcc())) {
                helper.setBcc(mail.getBcc());
            }

            // 2.3. Ghi tiêu đề và nội dung HTML [cite: 92, 93]
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            // 2.4. Đính kèm file từ danh sách cách nhau bởi dấu phẩy/chấm phẩy [cite: 95, 97, 99]
            String filenames = mail.getFilenames();
            if (!this.isNullOrEmpty(filenames)) {
                for (String filename : filenames.split("[,;]+")) {
                    File file = new File(filename.trim());
                    if (file.exists()) { // Kiểm tra file có tồn tại không để tránh lỗi
                        helper.addAttachment(file.getName(), file);
                    }
                }
            }

            // 3. Gửi Mail thực tế [cite: 64]
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // Không nên throw RuntimeException ở đây nếu dùng Scheduled để tránh dừng luồng
        }
    }

    // Xếp mail vào hàng đợi (Bài 2) [cite: 132, 134]
    @Override
    public void push(MailService.Mail mail) {
        queue.add(mail);
    }

    // Tự động gửi mail sau mỗi 500ms nếu hàng đợi có mail [cite: 135, 136, 138, 140]
    @Scheduled(fixedDelay = 500)
    public void run() {
        while (!queue.isEmpty()) {
            MailService.Mail mail = queue.remove(0);
            this.send(mail);
        }
    }

    private boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().length() == 0);
    }
}