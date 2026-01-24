package com.example.Assignment.repository;

import com.example.Assignment.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 1. Truy vấn sản phẩm theo loại hàng (Dùng cho trang Detail/Sản phẩm cùng loại)
    @Query("SELECT p FROM Product p WHERE p.category.id = :cid")
    List<Product> findByCategoryId(@Param("cid") String cid);

    // 2. TRUY VẤN TỔNG HỢP: Tìm theo tên VÀ Lọc theo loại (Sửa lỗi 400)
    // Giải thích: Nếu :cid là NULL (người dùng nhấn All Deals), điều kiện lọc loại sẽ được bỏ qua.
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:kw% AND (:cid IS NULL OR p.category.id = :cid)")
    List<Product> searchByNameAndCategory(@Param("kw") String keywords, @Param("cid") String categoryId);

    // 3. Tìm kiếm kết hợp PHÂN TRANG (Dành cho danh sách lớn)
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:kw% AND (:cid IS NULL OR p.category.id = :cid)")
    Page<Product> searchByNameAndCategoryPageable(@Param("kw") String keywords, @Param("cid") String categoryId, Pageable pageable);

    // 4. Tìm kiếm đơn giản theo từ khóa
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:kw%")
    List<Product> findByKeywords(@Param("kw") String keywords);
}