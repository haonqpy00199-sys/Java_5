package com.example.Lab7.DAO;

import com.example.Lab7.model.Product;
import com.example.Lab7.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    // Bài 1
    @Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
    List<Product> findByPrice(double minPrice, double maxPrice);

    // Bài 2
    @Query("SELECT o FROM Product o WHERE o.name LIKE ?1")
    Page<Product> findByKeywords(String keywords, Pageable pageable);

    @Query("SELECT o.category AS group, SUM(o.price) AS sum, COUNT(o) AS count "
            + " FROM Product o "
            + " GROUP BY o.category "
            + " ORDER BY SUM(o.price) DESC")
    List<Report> getInventoryByCategory();

    // BÀI 4: Sử dụng DSL thay cho @Query
    // Spring sẽ tự hiểu: tìm Product có thuộc tính price nằm giữa minPrice và maxPrice
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // BÀI 5: Sử dụng DSL thay cho @Query cho tìm kiếm và phân trang
    // Spring sẽ tự hiểu: Tìm tất cả sản phẩm có tên giống (Like) từ khóa và thực hiện phân trang
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);
}

