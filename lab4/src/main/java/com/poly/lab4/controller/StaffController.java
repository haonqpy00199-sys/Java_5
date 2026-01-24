package com.poly.lab4.controller;

import com.poly.lab4.model.Staff;
import jakarta.validation.Valid; // Thêm import này
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Thêm import này
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class StaffController {

    @RequestMapping("/staff/create/form")
    public String createForm(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "demo/staff-create";
    }

    @RequestMapping("/staff/create/save")
    public String createSave(Model model,
                             @Valid @ModelAttribute("staff") Staff staff,
                             BindingResult result, // BindingResult phải đứng ngay sau đối tượng được Valid
                             @RequestPart("photo_file") MultipartFile photoFile) {

        // 1. Kiểm tra nếu có lỗi validate
        if (result.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi nhập liệu dưới đây!");
            return "demo/staff-create"; // Trả về lại trang form để hiện lỗi
        }

        // 2. Xử lý file ảnh nếu không có lỗi
        if (!photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getOriginalFilename());
        }

        model.addAttribute("message", "Chúc mừng! Thêm mới thành công: " + staff.getFullname());
        return "demo/staff-create";
    }
}