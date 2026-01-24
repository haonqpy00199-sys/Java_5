package com.example.Assignment.repository;

import com.example.Assignment.entity.Category; // PHẢI IMPORT ĐÚNG ENTITY NÀY
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}