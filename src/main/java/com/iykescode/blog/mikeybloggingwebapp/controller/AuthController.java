package com.iykescode.blog.mikeybloggingwebapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String displayLogin(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model,
                               HttpServletRequest request) {
        model.addAttribute("servletPath", request);

        if(error != null) {
            model.addAttribute("errorMsg", "Username or Password is Incorrect !!");
        } else if(logout != null) {
            model.addAttribute("logoutMsg", "You have been successfully logged out !!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "auth/login";

        return "redirect:/";
    }
}
