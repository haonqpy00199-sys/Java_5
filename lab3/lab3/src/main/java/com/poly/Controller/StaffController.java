package com.poly.Controller;

import com.poly.model.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class StaffController {

    @RequestMapping("/staff/detail")
    public String detail (Model model) {
        Staff staff = Staff.builder()
                .id("haopy00199@gmail.com")                 // Hiển thị Email [cite: 13, 66]
                .fullname("nguyễn Quốc Hào")          // Sẽ được format in hoa [cite: 19, 67]
                .photo("photo.jpg")                   // Tên file ảnh trong thư mục static/photos
                .gender(true)                         // true để hiển thị "Nam" [cite: 21, 37]
                .birthday(new Date())                 // Ngày hiện tại [cite: 13, 39]
                .salary(12345.6789)                   // Lương để test định dạng số [cite: 13, 47]
                .level(2)                             // Level 2 tương ứng với "Tướng" [cite: 26, 43, 68]
                .build();

        model.addAttribute("staff", staff);       // Chia sẻ dữ liệu sang View [cite: 56, 70]
        return "demo/staff-detail";               // Trả về đúng tên file HTML [cite: 71]
    }

    @RequestMapping("/staff/list")
    public String list(Model model) {
        // Tạo danh sách List chứa nhiều đối tượng Staff [cite: 92]
        List<Staff> list = List.of(
                Staff.builder().id("user1@gmail.com").fullname("nguyễn Văn Hào").photo("photo.jpg").salary(12345.6789).level(0).build(),
                Staff.builder().id("user2@gmail.com").fullname("Lê Quốc Huy").photo("photo.jpg").salary(12345.6789).level(1).build(),
                Staff.builder().id("user3@gmail.com").fullname("Thái Quốc Việt").photo("photo.jpg").salary(12345.6789).level(2).build(),
                Staff.builder().id("user4@gmail.com").fullname("La Nguyễn Trọng Quý").photo("photo.jpg").salary(12345.6789).level(2).build(),
                Staff.builder().id("user5@gmail.com").fullname("nguyễn văn user5").photo("photo.jpg").salary(12345.6789).level(1).build(),
                Staff.builder().id("user6@gmail.com").fullname("nguyễn văn user6").photo("photo.jpg").salary(12345.6789).level(0).build()
        );

        model.addAttribute("list", list); // Chia sẻ danh sách với view [cite: 106]
        return "demo/staff-list"; // Trả về file staff-list.html [cite: 107]
    }

    @RequestMapping("/staff/list-status")
    public String listStatus(Model model) {
        List<Staff> list = List.of(
                Staff.builder().id("user1@gmail.com").fullname("nguyễn Văn Hào").level(0).build(),
                Staff.builder().id("user2@gmail.com").fullname("Lê Quốc Huy").level(1).build(),
                Staff.builder().id("user3@gmail.com").fullname("Thái Quốc Việt").level(2).build(),
                Staff.builder().id("user4@gmail.com").fullname("La Nguyễn Trọng Quý").level(2).build(),
                Staff.builder().id("user5@gmail.com").fullname("nguyễn văn user5").level(1).build(),
                Staff.builder().id("user6@gmail.com").fullname("nguyễn văn user6").level(0).build()
        );
        model.addAttribute("list", list);
        return "demo/list-status"; // Trả về view list-status.html
    }

    @RequestMapping("/staff/list-controls")
    public String listControls(Model model) {
        List<Staff> list = List.of(
                Staff.builder().id("user1@gmail.com").fullname("nguyễn Văn Hào").build(),
                Staff.builder().id("user2@gmail.com").fullname("Lê Quốc Huy").build(),
                Staff.builder().id("user3@gmail.com").fullname("Thái Quốc Việt").build(),
                Staff.builder().id("user4@gmail.com").fullname("La Nguyễn Trọng Quý").build(),
                Staff.builder().id("user5@gmail.com").fullname("nguyễn văn user5").build(),
                Staff.builder().id("user6@gmail.com").fullname("nguyễn văn user6").build()
        );
        model.addAttribute("list", list);
        return "demo/list-controls";
    }

    @RequestMapping("/staff/list-action")
    public String listAction(Model model) {
        // Khai báo và khởi tạo danh sách nhân viên
        List<Staff> list = List.of(
                Staff.builder().id("user1@gmail.com").fullname("nguyễn Văn Hào").salary(12345.0).level(0).build(),
                Staff.builder().id("user2@gmail.com").fullname("Lê Quốc Huy").salary(16000.0).level(1).build(),
                Staff.builder().id("user3@gmail.com").fullname("Thái Quốc Việt").salary(25000.0).level(2).build(),
                Staff.builder().id("user4@gmail.com").fullname("La Nguyễn Trọng Quý").salary(12345.0).level(0).build(),
                Staff.builder().id("user5@gmail.com").fullname("nguyễn văn user5").salary(16000.0).level(1).build(),
                Staff.builder().id("user6@gmail.com").fullname("nguyễn văn user6").salary(25000.0).level(2).build()

        );

        // Truyền danh sách vào model
        model.addAttribute("list", list);
        return "demo/staff-action";
    }
}