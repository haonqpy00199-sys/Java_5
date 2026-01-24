package com.example.Assignment.controller;

import com.example.Assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
    @Autowired
    CartService cart;

    @ModelAttribute("cart")
    public CartService getCart() {
        return cart; // Bây giờ bạn có thể dùng ${cart.count} ở bất cứ file HTML nào
    }
}