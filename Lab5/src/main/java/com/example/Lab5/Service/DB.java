package com.example.Lab5.Service;


import com.example.Lab5.Model.Item;

import java.util.HashMap;
import java.util.Map;

public class DB {
    public static Map<Integer, Item> items = new HashMap<>();

    static {
        // Khởi tạo dữ liệu mẫu
        items.put(1, new Item(1, "Samsung Galaxy S23", 1000.0, 0));
        items.put(2, new Item(2, "iPhone 15 Pro Max", 1200.0, 0));
        items.put(3, new Item(3, "iPad Air M2", 800.0, 0));
        items.put(4, new Item(4, "MacBook Pro M3", 2500.0, 0));
        items.put(5, new Item(5, "AirPods Pro 2", 250.0, 0));
    }
}