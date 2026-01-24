package web.shop.controller;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController2 {
    @Autowired
    ServletContext application;

    @RequestMapping("/url2.php")
    public String sayHello(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
        String fullname = request.getParameter("fullname");
        request.setAttribute("message", "Http Components");

        // ServletContext
        application.setAttribute("appName", "Spring Boot MVC Demo");

        // HttpSession
        session.setAttribute("username", "admin");

        // HttpServletRequest
        String userAgent = request.getHeader("User-Agent");

        // HttpServletResponse
        response.setHeader("Demo-Header", "SpringBootMVC");

        model.addAttribute("userAgent", userAgent);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("appName", application.getAttribute("appName"));

        return "hello";
    }
}