package com.example.Lab6.repository;

import com.example.Lab6.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, String> {}
