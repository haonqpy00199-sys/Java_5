package com.example.Assignment.controller.admin;

import com.example.Assignment.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ReportAController {
    @Autowired
    OrderDetailRepository dRepo;

    @GetMapping("/index")
    public String index(Model model) {
        List<Object[]> list = dRepo.getRevenueByCategory();
        model.addAttribute("items", list);

        // Tách dữ liệu để vẽ biểu đồ
        List<String> labels = list.stream().map(row -> row[0].toString()).toList();
        List<Double> values = list.stream().map(row -> (Double) row[1]).toList();

        model.addAttribute("labels", labels);
        model.addAttribute("values", values);

        model.addAttribute("view", "admin/report/index");
        return "layout/index";
    }


}