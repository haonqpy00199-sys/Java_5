package com.example.Assignment.repository;

import com.example.Assignment.entity.Account;
import com.example.Assignment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 1. Tìm danh sách đơn hàng của một tài khoản cụ thể.
     * Hàm này giúp giải quyết lỗi gạch đỏ oRepo.findByAccount(user) trong Controller.
     */
    List<Order> findByAccount(Account user);

    /**
     * 2. Tìm đơn hàng theo Account và sắp xếp mới nhất lên đầu.
     * Giúp giao diện list.html hiển thị lịch sử mua hàng hợp lý hơn.
     */
    List<Order> findByAccountOrderByCreateDateDesc(Account user);

    /**
     * 3. Tìm đơn hàng dựa trên Username của tài khoản (Dùng JPQL).
     */
    @Query("SELECT o FROM Order o WHERE o.account.username = ?1 ORDER BY o.createDate DESC")
    List<Order> findByUsername(String username);

    /**
     * 4. Truy vấn phục vụ báo cáo doanh thu theo khách hàng (Dùng cho Giai đoạn Admin).
     * Trả về danh sách gồm Username và Tổng tiền (Sum = Price * Quantity).
     */
    @Query("SELECT o.account.username, SUM(d.price * d.quantity) " +
            "FROM Order o JOIN o.orderDetails d " +
            "GROUP BY o.account.username " +
            "ORDER BY SUM(d.price * d.quantity) DESC")
    List<Object[]> getRevenueByCustomer();
}