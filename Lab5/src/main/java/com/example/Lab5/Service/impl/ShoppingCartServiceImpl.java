package com.example.Lab5.Service.impl;

import com.example.Lab5.Model.Item;
import com.example.Lab5.Service.DB;
import com.example.Lab5.Service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {
    // Map dùng để lưu trữ: Key là ID sản phẩm, Value là đối tượng Item
    Map<Integer, Item> map = new HashMap<>();

    @Override
    public Item add(Integer id) {
        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Item item = map.get(id);
        if (item == null) {
            // Nếu chưa có, lấy thông tin từ DB (ở đây giả lập lấy từ lớp DB của Lab)
            item = DB.items.get(id);
            item.setQty(1);
            map.put(id, item);
        } else {
            // Nếu có rồi thì tăng số lượng lên 1
            item.setQty(item.getQty() + 1);
        }
        return item;
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public Item update(Integer id, int qty) {
        Item item = map.get(id);
        item.setQty(qty);
        return item;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Item> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        return map.values().stream()
                .mapToInt(item -> item.getQty())
                .sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQty())
                .sum();
    }
}
