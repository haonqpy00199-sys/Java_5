package com.poly.Slide7.repositorys;

import com.poly.Slide7.entity.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface productDAO extends JpaRepository< product, Integer> {
    @Query(value = "SELECT * from Product where Unit_Price > 50 and Unit_Price < 200", nativeQuery = true)
 List<product> finByUnitPriceRanger();

}
