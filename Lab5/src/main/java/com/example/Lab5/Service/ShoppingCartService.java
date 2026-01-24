package com.example.Lab5.Service;

import com.example.Lab5.Model.Item;

import java.util.Collection;

public interface ShoppingCartService {
    Item add(Integer id);           // Thêm sản phẩm
    void remove(Integer id);        // Xóa sản phẩm
    Item update(Integer id, int qty); // Cập nhật số lượng
    void clear();                   // Xóa sạch giỏ hàng
    Collection<Item> getItems();    // Lấy danh sách sản phẩm
    int getCount();                 // Tổng số lượng các mặt hàng
    double getAmount();             // Tổng số tiền
}
