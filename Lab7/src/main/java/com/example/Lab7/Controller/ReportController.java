package com.example.Lab7.Controller;

import com.example.Lab7.DAO.ProductDAO;
import com.example.Lab7.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ReportController {
    @Autowired
    ProductDAO dao;

    @RequestMapping("/report/inventory-by-category")
    public String inventory(Model model) {
        // Gọi phương thức tổng hợp từ DAO
        List<Report> items = dao.getInventoryByCategory();
        model.addAttribute("items", items);
        return "report/inventory-by-category";
    }
}
