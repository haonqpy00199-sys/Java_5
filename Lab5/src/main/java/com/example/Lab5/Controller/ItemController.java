package com.example.Lab5.Controller;

import com.example.Lab5.Service.DB;
import com.example.Lab5.Service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class ItemController {
    @Autowired
    ParamService paramService; // Dùng để lưu file

    @GetMapping("/item/upload")
    public String upload() {
        return "item/upload"; // Trả về view upload.html
    }

    @RequestMapping("/item/index")
    public String list(Model model) {
        model.addAttribute("items", DB.items.values());
        return "item/index";
    }

        @PostMapping("/item/upload")
    public String upload(@RequestParam("attach") MultipartFile attach, Model model) {
        if (!attach.isEmpty()) {
            // Lưu file vào thư mục "images" trong thư mục gốc của server
            // File sẽ được lưu tại: /src/main/webapp/images/ hoặc đường dẫn thực thi
            File savedFile = paramService.save(attach, "/images/");

            if (savedFile != null) {
                model.addAttribute("message", "Upload thành công: " + savedFile.getName());
                model.addAttribute("filename", savedFile.getName());
            } else {
                model.addAttribute("message", "Upload thất bại!");
            }
        }
        return "item/upload";
    }
}