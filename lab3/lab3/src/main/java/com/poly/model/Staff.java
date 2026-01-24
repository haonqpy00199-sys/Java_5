package com.poly.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    private String id;
    private String fullname;
    private String photo = "photo.jpg";
    private Boolean gender = true;
    private Date birthday = new Date();
    private Double salary = 12345.6789;
    private Integer level = 0;
}