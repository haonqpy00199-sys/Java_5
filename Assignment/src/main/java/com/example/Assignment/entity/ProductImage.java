package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude // Ngăn chặn lỗi lặp vô tận khi in log
    private Product product;
}