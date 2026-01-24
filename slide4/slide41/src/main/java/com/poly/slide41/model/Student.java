package com.poly.slide41.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {
    @NotBlank(message = "Chưa nhâpj Email")
@   Email(message = "Không đúng định dạng Email")
    private String id;
    @NotBlank(message = "Chưa nhập họ tên")
    private String fullname;
    @NotNull(message = "Chưa chọn giới tính")
    private Boolean gender;

    @NotNull(message = "Chưa chọn ngày sinh")
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday = new Date();

    @NotNull(message = "chưa nhập hình ảnh")
    @Builder.Default
    private String photo = "photo.png";

    @NotNull(message = "Chưa nhập điểm ")
    @Range(min = 0, max = 10, message = "Điểm phải từ 1 đến 10")
    private Double mark;









}