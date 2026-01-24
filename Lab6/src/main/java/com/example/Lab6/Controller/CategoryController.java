package com.example.Lab6.Controller;

import com.example.Lab6.Model.Category;
import com.example.Lab6.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryDAO dao; // Tiêm interface DAO đã tạo ở Bài 1

    @RequestMapping("/category/index")
    public String index(Model model) {
        Category item = new Category();
        model.addAttribute("item", item); // Đối tượng dùng cho Form [cite: 159, 164]
        List<Category> items = dao.findAll();
        model.addAttribute("items", items); // Danh sách hiển thị lên Bảng [cite: 159, 165]
        return "category/index";
    }

    @RequestMapping("/category/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        Category item = dao.findById(id).get(); // Tìm dữ liệu theo ID [cite: 221]
        model.addAttribute("item", item);
        List<Category> items = dao.findAll();
        model.addAttribute("items", items);
        return "category/index";
    }

    @RequestMapping("/category/create")
    public String create(Category item) {
        dao.save(item); // Thêm mới vào CSDL [cite: 231]
        return "redirect:/category/index";
    }

    @RequestMapping("/category/update")
    public String update(Category item) {
        dao.save(item); // Cập nhật dựa trên ID có sẵn [cite: 236]
        return "redirect:/category/edit/" + item.getId();
    }

    @RequestMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        dao.deleteById(id); // Xóa bản ghi [cite: 241]
        return "redirect:/category/index";
    }
}