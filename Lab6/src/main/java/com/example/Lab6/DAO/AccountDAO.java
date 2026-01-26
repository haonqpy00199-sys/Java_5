package com.example.Lab6.DAO;

import com.example.Lab6.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, String> {}
