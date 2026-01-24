package com.poly1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResultController {

    @RequestMapping("/a")
    public String m1() {
        return "a"; // Trả về view a.html
    }

    @RequestMapping("/b")
    public String m2(Model model) {
        model.addAttribute("message", "I come from b");
        return "forward:/a"; // ?1: Chuyển tiếp nội bộ, giữ nguyên Model
    }

    @RequestMapping("/c")
    public String m3(RedirectAttributes params) {
        params.addAttribute("message", "I come from c");
        return "redirect:/a"; // ?2: Chuyển hướng, message sẽ xuất hiện trên URL (?message=I...)
    }

    @ResponseBody // ?3: Cho phép trả về chuỗi văn bản thay vì tên View
    @RequestMapping("/d")
    public String m4() {
        return "I come from d";
    }
}
