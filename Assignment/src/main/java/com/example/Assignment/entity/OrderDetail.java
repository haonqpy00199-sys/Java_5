package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Orderdetails")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double price;
    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "Productid")
    Product product; // Sản phẩm được mua [cite: 67]

    @ManyToOne
    @JoinColumn(name = "Orderid")
    Order order; // Thuộc đơn hàng nào [cite: 69]
}
