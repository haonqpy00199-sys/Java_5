package com.poly.Slide7.controller;

import com.poly.Slide7.entity.product;
import com.poly.Slide7.service.interfaces.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class productController {
    @Autowired
    private productService productService;

    @GetMapping ("/product")
    public String getProduct(Model model){
        List<product> products= productService.findAll();

    //    double min = 1.00;
   //     double max = 20.00;

    model.addAttribute("products",products);
    return "product.html";
    }
}
