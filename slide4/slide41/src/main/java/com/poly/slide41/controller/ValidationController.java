package com.poly.slide41.controller;

import com.poly.slide41.model.Student;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ValidationController {
    @GetMapping("/student/form")
    public String showForm(Model model, @ModelAttribute("student") Student student){
        model.addAttribute("message", "Enter student info please!");
        return "/demo/student";

    }
    @PostMapping("/student/save")
    public String saveStudent(Model model,@ModelAttribute @Valid Student student, Errors error){
        if (error.hasErrors()){
            model.addAttribute("message","Vui lòng sửa các lỗi sau!");
        }else{
            model.addAttribute("message","Dữ liệu đã nhập đúng");
        }
        return "/demo/student";
    }
}
