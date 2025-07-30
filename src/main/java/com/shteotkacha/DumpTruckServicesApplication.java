package com.shteotkacha;

import com.shteotkacha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
    org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration.class,
    org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration.class
})
public class DumpTruckServicesApplication {
    
    @Autowired
    private UserService userService;
    
    public static void main(String[] args) {
        SpringApplication.run(DumpTruckServicesApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            try {
                // Create admin user if it doesn't exist
                userService.createAdminUser(
                    "Admin User",
                    "admin@shteotkacha.com",
                    "admin123",
                    "+1234567890"
                );
                System.out.println("✅ Admin user created successfully!");
                System.out.println("📧 Email: admin@shteotkacha.com");
                System.out.println("🔑 Password: admin123");
                System.out.println("🔗 Access admin dashboard at: http://localhost:8080/admin");
            } catch (Exception e) {
                System.out.println("ℹ️ Admin user already exists or error occurred: " + e.getMessage());
            }
        };
    }
} 