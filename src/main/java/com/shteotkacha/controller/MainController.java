package com.shteotkacha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @GetMapping({"/", "/home"})
    public String homePage() {
        return "main/home";
    }
    
    @GetMapping("/contact")
    public String contactPage() {
        return "main/contact";
    }
} 