package server.team_a.todayhouse.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login-form";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("LoginError", true);
        return "login-form";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/api/testing")
    @ResponseBody
    public String securityPassed(){
        return "security login passed";
    }

    @GetMapping("/auth")
    @ResponseBody
    public Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/user-page")
    public String userPage() {
        return "user-page";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/admin-page")
    public String adminPage() {
        return "admin-page";
    }
}
