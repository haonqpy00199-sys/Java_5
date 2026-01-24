package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

    // Lưu ý: Nhờ có @Data của Lombok, bạn không cần viết thủ công
    // getQty() và setQty() nữa. Nó sẽ tự động được tạo ra.
}