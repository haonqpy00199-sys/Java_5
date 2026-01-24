package com.example.Assignment.service;
import com.example.Assignment.entity.Product;
import java.util.Collection;

public interface CartService {
    void add(Integer id);
    void remove(Integer id);
    void update(Integer id, int qty);
    void clear();
    Collection<Product> getItems();
    int getCount();
    double getAmount();
}