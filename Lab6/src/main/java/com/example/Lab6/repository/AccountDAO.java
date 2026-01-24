package com.example.Lab6.repository;

import com.example.Lab6.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, String> {}