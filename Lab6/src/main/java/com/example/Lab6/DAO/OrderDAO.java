package com.example.Lab6.DAO;

import com.example.Lab6.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Long> {}

