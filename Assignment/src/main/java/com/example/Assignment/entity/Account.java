package com.example.Assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    String username;
    String password;
    String fullname;
    String email;
    String photo;
    Boolean activated;
    Boolean admin; // Phân quyền admin và người dùng [cite: 56, 145]

    // Trong file Account.java
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL) // Đổi từ "templates/account" thành "account"
            List<Order> orders;
}
