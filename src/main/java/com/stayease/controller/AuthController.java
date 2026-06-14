package com.stayease.controller;

import com.stayease.model.Staff;
import com.stayease.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @GetMapping({"/", "/login"})
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        Staff staff = authService.login(username, password);
        if (staff == null) {
            model.addAttribute("error", "Username/email atau password salah.");
            return "login";
        }
        session.setAttribute("staff", staff);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
