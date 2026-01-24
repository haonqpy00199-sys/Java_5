package com.example.Assignment.ServiceImpl;

import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.ProductRepository;
import com.example.Assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SessionScope // Giỏ hàng duy nhất cho mỗi phiên làm việc của người dùng
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository pRepo;

    // Map dùng để lưu trữ sản phẩm trong giỏ (Key: ID sản phẩm, Value: Đối tượng Product)
    Map<Integer, Product> map = new HashMap<>();

    @Override
    public void add(Integer id) {
        Product p = map.get(id);
        if (p == null) {
            // Nếu chưa có trong giỏ, lấy từ DB và đặt số lượng là 1 [cite: 26]
            p = pRepo.findById(id).get();
            p.setQty(1);
            map.put(id, p);
        } else {
            // Nếu đã có, tăng số lượng lên 1
            p.setQty(p.getQty() + 1);
        }
    }

    @Override
    public void remove(Integer id) {
        // Xóa mặt hàng khỏi giỏ [cite: 29]
        map.remove(id);
    }

    @Override
    public void update(Integer id, int qty) {
        // Cập nhật số lượng mới cho mặt hàng
        Product p = map.get(id);
        if (p != null) {
            p.setQty(qty);
            if (p.getQty() <= 0) {
                map.remove(id);
            }
        }
    }

    @Override
    public void clear() {
        // Xóa sạch toàn bộ giỏ hàng
        map.clear();
    }

    @Override
    public Collection<Product> getItems() {
        // Trả về danh sách các mặt hàng để hiển thị [cite: 27]
        return map.values();
    }

    @Override
    public int getCount() {
        // Tổng số lượng tất cả các món đồ trong giỏ
        return map.values().stream()
                .mapToInt(p -> p.getQty()).sum();
    }

    @Override
    public double getAmount() {
        // Tổng thành tiền (Giá x Số lượng)
        return map.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQty()).sum();
    }
}