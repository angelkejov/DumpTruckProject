package com.shteotkacha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @GetMapping("/ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }
} 