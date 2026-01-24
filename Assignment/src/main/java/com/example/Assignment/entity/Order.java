package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Createdate")
    Date createDate = new Date();

    // Trong file Order.java
    @ManyToOne
    @JoinColumn(name = "username") // Tên cột khóa ngoại trong DB
            Account account; // Tên biến này phải trùng với giá trị 'mappedBy' ở lớp Account

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails; // Chi tiết các mặt hàng trong đơn [cite: 70]
}
