package com.shteotkacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DumpTruckServicesApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Starting Dump Truck Services Application...");
        SpringApplication.run(DumpTruckServicesApplication.class, args);
        System.out.println("✅ Application started successfully!");
    }
} 