package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String image;
    Double price;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();

    Boolean available;

    @ManyToOne
    @JoinColumn(name = "Categoryid")
    Category category; // Thuộc về một loại hàng nhất định [cite: 24, 67]

    /**
     * Trường này dùng để lưu số lượng sản phẩm trong giỏ hàng.
     * @Transient: JPA sẽ bỏ qua trường này, không lưu vào database.
     */
    @Transient
    Integer qty = 1;

    @Column(columnDefinition = "nvarchar(MAX)") // Cho phép lưu văn bản dài và có dấu
    String description;

    // Trong file Product.java
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    // Helper method để thêm ảnh dễ dàng hơn
    public void addProductImage(ProductImage image) {
        productImages.add(image);
        image.setProduct(this);
    }
}