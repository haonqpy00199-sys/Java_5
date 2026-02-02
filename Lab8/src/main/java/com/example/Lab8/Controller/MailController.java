package com.example.Lab8.Controller;

import com.example.Lab8.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {
    @Autowired
    MailService mailService;

    @GetMapping("/mail/index")
    public String index() {
        return "mail/form";
    }

    @PostMapping("/mail/send")
    public String send(Model model,
                       @RequestParam String to,
                       @RequestParam(defaultValue = "") String cc,
                       @RequestParam(defaultValue = "") String bcc,
                       @RequestParam String subject,
                       @RequestParam String body,
                       @RequestParam("files") MultipartFile[] files,
                       @RequestParam String action) {

        // 1. Xử lý file đính kèm
        List<String> listFiles = new ArrayList<>();
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if(!dir.exists()) dir.mkdirs();

        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        File savedFile = new File(uploadDir + file.getOriginalFilename());
                        file.transferTo(savedFile);
                        listFiles.add(savedFile.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 2. Tạo đối tượng Mail (Sử dụng Builder từ bài 1) [cite: 14, 22]
        MailService.Mail mail = MailService.Mail.builder()
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .filenames(String.join(", ", listFiles))
                .build();

        // 3. Xử lý nút bấm dựa trên giá trị của 'action'
        if ("send".equalsIgnoreCase(action)) {
            mailService.send(mail); // Gửi trực tiếp [cite: 19]
            model.addAttribute("message", "Đã gửi trực tiếp thành công!");
        } else {
            mailService.push(mail); // Xếp vào hàng đợi (Bài 2) [cite: 125, 134]
            model.addAttribute("message", "Đã thêm vào hàng đợi!");
        }

        return "mail/form";
    }

    @GetMapping("/mail/history")
    public String history(Model model) {
        model.addAttribute("mails", mailService.getSentMails());
        return "mail/history";
    }


}