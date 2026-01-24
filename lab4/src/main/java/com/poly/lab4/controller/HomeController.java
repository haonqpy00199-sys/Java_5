package com.poly.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home/index")
    public String index(Model model) {
        // Trả về đúng đường dẫn file trong templates/home/index.html
        return "home/index";
    }

    @RequestMapping("/home/about")
    public String about(Model model) {
        // Trả về đúng đường dẫn file trong templates/home/about.html
        return "home/about";
    }
}