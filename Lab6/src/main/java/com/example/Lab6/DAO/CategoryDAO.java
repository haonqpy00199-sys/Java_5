package com.example.Lab6.DAO;

import com.example.Lab6.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository <Category, String>{
}
