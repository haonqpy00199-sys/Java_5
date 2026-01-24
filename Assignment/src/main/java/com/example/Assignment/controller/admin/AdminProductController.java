package com.example.Assignment.controller.admin;

import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.CategoryRepository;
import com.example.Assignment.repository.ProductRepository;
import com.example.Assignment.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired ProductRepository pRepo;
    @Autowired CategoryRepository cRepo;
    @Autowired UploadService uploadService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", pRepo.findAll());
        model.addAttribute("view", "admin/product/index");
        return "layout/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", cRepo.findAll());
        model.addAttribute("view", "admin/product/form");
        return "layout/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("product", pRepo.findById(id).get());
        model.addAttribute("categories", cRepo.findAll());
        model.addAttribute("view", "admin/product/form");
        return "layout/index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("product") Product product,
                       @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            // Nếu có ảnh mới, lưu file vật lý và gán tên mới cho product
            String fileName = uploadService.save(imageFile);
            product.setImage(fileName);
        } else if (product.getId() != null) {
            // Nếu không có ảnh mới và là cập nhật, lấy lại tên ảnh cũ từ DB
            pRepo.findById(product.getId()).ifPresent(old -> product.setImage(old.getImage()));
        }

        if (product.getId() == null) product.setCreateDate(new Date());

        pRepo.save(product);
        return "redirect:/admin/product/index?message=Success!";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pRepo.deleteById(id);
        return "redirect:/admin/product/index";
    }
}