package com.example.Assignment.repository;

import com.example.Assignment.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    // Khai báo phương thức để Controller có thể gọi dRepo.findByOrderId(id)
    @Query("SELECT d FROM OrderDetail d WHERE d.order.id = ?1")
    List<OrderDetail> findByOrderId(Long orderId);

    // TRUY VẤN THỐNG KÊ DOANH THU THEO LOẠI
    // Trả về: Tên loại, Tổng doanh thu, Tổng số lượng đã bán
    @Query("SELECT d.product.category.name, SUM(d.price * d.quantity), SUM(d.quantity) " +
            "FROM OrderDetail d " +
            "GROUP BY d.product.category.name")
    List<Object[]> getRevenueByCategory();
}