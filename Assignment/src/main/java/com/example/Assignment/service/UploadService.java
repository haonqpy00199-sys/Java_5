package com.example.Assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {
    // 1. Xác định thư mục gốc của dự án để lưu trực tiếp vào src
    private final String uploadFolder = "src/main/resources/static/images/";

    public String save(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // 2. Lấy đường dẫn tuyệt đối để tránh lỗi file không tồn tại khi lưu
            Path root = Paths.get(uploadFolder);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            // 3. Tạo tên file duy nhất bằng UUID để tránh trùng lặp ảnh
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(root.toAbsolutePath().toString() + File.separator + fileName);

            // 4. Lưu file vật lý xuống ổ cứng
            file.transferTo(dest);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}