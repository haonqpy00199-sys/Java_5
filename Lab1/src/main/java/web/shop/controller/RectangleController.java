package web.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rectangle")

public class RectangleController {

    @Autowired
    HttpServletRequest request;

    // 👉 Hiển thị form
    @GetMapping("/form")
    public String form() {
        return "demo/rectangle";
    }

    //  Xử lý tính toán
    @PostMapping("/calc")
    public String calc(Model model) {

        double length = Double.parseDouble(request.getParameter("length"));
        double width  = Double.parseDouble(request.getParameter("width"));

        //  Logic kiểm tra
        if (width > length) {
            model.addAttribute("error", "Chiều rộng không được lớn hơn chiều dài!");
            return "demo/rectangle";
        }

        //  Tính toán
        double area = length * width;
        double perimeter = (length + width) * 2;

        model.addAttribute("area", area);
        model.addAttribute("perimeter", perimeter);

        return "demo/rectangle";
    }

}
