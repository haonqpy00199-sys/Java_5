package com.example.Lab6.Controller;

import com.example.Lab6.DAO.ProductDAO;
import com.example.Lab6.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class    ProductController {
    @Autowired
    ProductDAO dao;

    @RequestMapping("/product/sort")
    public String sort(Model model,
                       @RequestParam("field") Optional<String> field,
                       @RequestParam("dir") Optional<String> dir) {

        // 1. Xác định trường cần sắp (mặc định là price)
        String fieldName = field.orElse("price");

        // 2. Xác định chiều sắp xếp (mặc định là asc - nhỏ đến lớn)
        String direction = dir.orElse("asc");

        // 3. Tạo đối tượng Sort dựa trên chiều người dùng chọn
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(Sort.Direction.DESC, fieldName) :
                Sort.by(Sort.Direction.ASC, fieldName);

        // 4. Gửi dữ liệu lên View
        model.addAttribute("items", dao.findAll(sort));
        model.addAttribute("field", fieldName.toUpperCase());

        // Gửi chiều hiện tại và chiều ngược lại để View tạo Link đổi chiều
        model.addAttribute("currentDir", direction);
        model.addAttribute("reverseDir", direction.equals("asc") ? "desc" : "asc");

        return "product/sort";
    }

    @RequestMapping("/product/page")
    public String paginate(Model model,
                           @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> page = dao.findAll(pageable);
        model.addAttribute("page", page);
        return "product/page";
    }
}