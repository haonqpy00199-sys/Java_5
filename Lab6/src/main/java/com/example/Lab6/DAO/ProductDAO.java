package com.example.Lab6.DAO;

import com.example.Lab6.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {}
