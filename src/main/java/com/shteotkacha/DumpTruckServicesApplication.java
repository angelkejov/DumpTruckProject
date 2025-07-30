package com.shteotkacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
    SystemMetricsAutoConfiguration.class
})
public class DumpTruckServicesApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Starting Dump Truck Services Application...");
        System.out.println("📊 Health check will be available at: /api/ping");
        System.out.println("🔍 Environment check will be available at: /api/env");
        
        try {
            SpringApplication.run(DumpTruckServicesApplication.class, args);
            System.out.println("✅ Application started successfully!");
        } catch (Exception e) {
            System.err.println("❌ Application failed to start: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
} 