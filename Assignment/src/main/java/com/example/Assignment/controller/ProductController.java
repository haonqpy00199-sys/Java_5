package com.example.Assignment.controller;

import com.example.Assignment.entity.Category;
import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.CategoryRepository;
import com.example.Assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired ProductRepository pRepo;
    @Autowired CategoryRepository cRepo;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return cRepo.findAll();
    }

    @RequestMapping("/home/index")
    public String index(Model model) {
        model.addAttribute("items", pRepo.findAll());
        model.addAttribute("view", "home/index");
        return "layout/index";
    }

    @RequestMapping("/product/search")
    public String search(
            @RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
            @RequestParam(value = "category", required = false) String categoryId,
            Model model) {
        List<Product> list = pRepo.searchByNameAndCategory(keywords, categoryId);
        model.addAttribute("items", list);
        model.addAttribute("keywords", keywords);
        model.addAttribute("view", "product/index");
        return "layout/index";
    }

    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Optional<Product> product = pRepo.findById(id);
        if (product.isPresent()) {
            Product item = product.get();
            model.addAttribute("item", item); // Thymeleaf sẽ dùng item.productImages

            List<Product> relatedItems = pRepo.findByCategoryId(item.getCategory().getId());
            model.addAttribute("relatedItems", relatedItems);
        }
        model.addAttribute("view", "product/detail");
        return "layout/index";
    }

    // ĐÃ XÓA PHẦN @PostMapping("/admin/product/save") TẠI ĐÂY ĐỂ TRÁNH LỖI TRÙNG LẶP
}