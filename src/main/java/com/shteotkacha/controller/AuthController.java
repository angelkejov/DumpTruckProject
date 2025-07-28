package com.shteotkacha.controller;

import com.shteotkacha.dto.UserRegistrationDto;
import com.shteotkacha.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        return "auth/login";
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto registrationDto,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        if (!registrationDto.isPasswordMatching()) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "auth/register";
        }
        
        try {
            userService.registerUser(registrationDto.getName(), registrationDto.getEmail(), registrationDto.getPassword());
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Please check your email for the verification code.");
            return "redirect:/verify";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
    
    @GetMapping("/verify")
    public String verifyPage(Model model) {
        return "auth/verify";
    }
    
    @PostMapping("/verify")
    public String verifyEmail(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        boolean verified = userService.verifyEmail(code);
        
        if (verified) {
            redirectAttributes.addFlashAttribute("success", "Email verified successfully! You can now log in.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired verification code.");
        }
        
        return "redirect:/login";
    }
} 