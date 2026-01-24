package com.poly1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ctrl")
public class OkController {

    @PostMapping("/ok")
    public String m1(Model model) {
        model.addAttribute("method", "m1() - POST");
        return "ok";
    }

    @GetMapping("/ok")
    public String m2(Model model) {
        model.addAttribute("method", "m2() - GET");
        return "ok";
    }

    @GetMapping(value = "/ok", params = "x")
    public String m3(Model model) {
        model.addAttribute("method", "m3() - GET + param x");
        return "ok";
    }
}
