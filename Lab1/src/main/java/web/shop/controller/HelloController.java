package web.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloController { // để bị gọi
    @RequestMapping("/poly/hello")
    public String sayHello(Model model) {
        model.addAttribute("title", "FPT Polytechnic"); // title là tên biến lấy ra mà dùng
        model.addAttribute("subject", "Spring boot MVC");
        return "demo/hello.html";
    }
}