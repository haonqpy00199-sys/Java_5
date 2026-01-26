package com.example.Lab8.ServiceImpl;

import com.example.Lab8.Service.AccountService;
import com.example.Lab8.entity.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // QUAN TRỌNG: Phải có dòng này để Spring tìm thấy Bean
public class AccountServiceImpl implements AccountService {

    // Giả lập danh sách tài khoản để đăng nhập (Vì bạn chưa cấu hình Database)
    private static List<Account> accounts = new ArrayList<>();

    static {
        Account acc = new Account();
        acc.setUsername("admin");
        acc.setPassword("123");
        acc.setFullname("Quản trị viên");
        acc.setEmail("admin@gmail.com");
        acc.setAdmin(true);
        accounts.add(acc);
    }

    @Override
    public Account findById(String username) {
        // Tìm tài khoản trong danh sách giả lập
        return accounts.stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
}