package com.example.Lab6.repository;


import com.example.Lab6.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Long> {}

