package com.poly.Slide7.service.impl;

import com.poly.Slide7.entity.product;
import com.poly.Slide7.repositorys.productDAO;
import com.poly.Slide7.service.interfaces.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productServiceImpl implements productService {
    @Autowired
    private productDAO productDAO;
    public List<product> findAll(){
        List<product> products = productDAO.findAll();
        return products;
    }
}
