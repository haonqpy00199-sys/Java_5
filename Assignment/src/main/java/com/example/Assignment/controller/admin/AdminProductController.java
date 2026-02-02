package com.example.Assignment.controller.admin;

import com.example.Assignment.entity.Product;
import com.example.Assignment.entity.ProductImage; // Entity cho bảng ảnh phụ
import com.example.Assignment.repository.CategoryRepository;
import com.example.Assignment.repository.ProductImageRepository; // Repository cho bảng ảnh phụ
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
    @Autowired ProductImageRepository piRepo; // Inject repository quản lý ảnh phụ
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
        // Dùng orElse(null) an toàn hơn .get()
        model.addAttribute("product", pRepo.findById(id).orElse(null));
        model.addAttribute("categories", cRepo.findAll());
        model.addAttribute("view", "admin/product/form");
        return "layout/index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("product") Product product,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "moreFiles", required = false) MultipartFile[] moreFiles) {

        // 1. Xử lý ảnh đại diện chính (imageFile)
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = uploadService.save(imageFile);
            product.setImage(fileName);
        } else if (product.getId() != null) {
            // Nếu không chọn ảnh mới, lấy lại tên ảnh cũ từ DB để không bị null
            pRepo.findById(product.getId()).ifPresent(old -> product.setImage(old.getImage()));
        }

        // 2. Thiết lập ngày tạo
        if (product.getId() == null) {
            product.setCreateDate(new Date());
        }

        // 3. Lưu sản phẩm trước để có ID (quan trọng để liên kết ảnh phụ)
        Product savedProduct = pRepo.save(product);

        // 4. Xử lý danh sách ảnh phụ (moreFiles)
        if (moreFiles != null && moreFiles.length > 0) {
            for (MultipartFile file : moreFiles) {
                if (!file.isEmpty()) {
                    String fileName = uploadService.save(file);

                    ProductImage pi = new ProductImage();
                    pi.setImageName(fileName);
                    pi.setProduct(savedProduct); // Liên kết với sản phẩm vừa lưu
                    piRepo.save(pi);
                }
            }
        }

        return "redirect:/admin/product/index?message=Success!";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pRepo.deleteById(id);
        return "redirect:/admin/product/index";
    }
}