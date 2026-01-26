package com.example.Lab7.model;


import java.io.Serializable;

public interface Report {
    Serializable getGroup(); // Chứa tên loại hàng (Category)
    Double getSum();         // Tổng giá
    Long getCount();        // Số sản phẩm
}