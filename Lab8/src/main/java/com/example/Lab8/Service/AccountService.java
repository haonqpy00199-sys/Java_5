package com.example.Lab8.Service;

import com.example.Lab8.entity.Account;

public interface AccountService {
    Account findById(String username); // Phương thức để tìm tài khoản khi đăng nhập [cite: 206]
}