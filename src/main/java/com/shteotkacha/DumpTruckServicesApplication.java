package com.shteotkacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DumpTruckServicesApplication {

    public static void main(String[] args) {
        // Force rebuild to remove H2 dependency
        SpringApplication.run(DumpTruckServicesApplication.class, args);
    }
} 