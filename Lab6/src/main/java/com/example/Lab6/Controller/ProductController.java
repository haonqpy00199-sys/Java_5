package com.example.Lab6.Controller;



import com.example.Lab6.repository.CategoryDAO;
import com.example.Lab6.repository.ProductDAO;
import com.example.Lab6.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductDAO productDao; // Làm việc với bảng Products [cite: 269]

    @Autowired
    CategoryDAO categoryDao; // Thêm DAO này để lấy dữ liệu Category [cite: 153]

    @RequestMapping("/product/sort")
    public String sort(Model model, @RequestParam("field") Optional<String> field) {
        // Xử lý sắp xếp sản phẩm [cite: 309, 310]
        String sortField = field.orElse("price");
        Sort sort = Sort.by(Direction.DESC, sortField);

        // Gửi danh sách sản phẩm đã sắp xếp [cite: 312, 313]
        model.addAttribute("items", productDao.findAll(sort));
        model.addAttribute("field", sortField.toUpperCase());

        // Gửi danh sách các loại mặt hàng (Category)
        model.addAttribute("categories", categoryDao.findAll());

        return "product/sort";
    }
}