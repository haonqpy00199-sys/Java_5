package com.example.Assignment.ServiceImpl;

import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.ProductRepository;
import com.example.Assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * CartServiceImpl: Xử lý logic giỏ hàng lưu trữ trong Session
 * proxyMode là bắt buộc để Thymeleaf có thể gọi đến các phương thức như getCount()
 */
@Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository pRepo;

    // Sử dụng Map để quản lý sản phẩm (Key là ID sản phẩm)
    Map<Integer, Product> map = new HashMap<>();

    @Override
    public void add(Integer id) {
        Product p = map.get(id);
        if (p == null) {
            // ĐÃ SỬA: Dùng orElse(null) và kiểm tra null để tránh lỗi No value present
            Product productFromDb = pRepo.findById(id).orElse(null);
            if (productFromDb != null) {
                productFromDb.setQty(1); // Đảm bảo entity Product có trường @Transient Integer qty
                map.put(id, productFromDb);
            }
        } else {
            p.setQty(p.getQty() + 1);
        }
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public void update(Integer id, int qty) {
        Product p = map.get(id);
        if (p != null) {
            if (qty > 0) {
                p.setQty(qty);
            } else {
                map.remove(id); // Nếu số lượng <= 0 thì xóa khỏi giỏ
            }
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Product> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        // Tính tổng số lượng tất cả sản phẩm
        return map.values().stream()
                .mapToInt(p -> p.getQty())
                .sum();
    }

    @Override
    public double getAmount() {
        // Tính tổng tiền giỏ hàng
        return map.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQty())
                .sum();
    }
}