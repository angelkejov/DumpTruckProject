package com.shteotkacha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired
    private Environment environment;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        health.put("message", "Application is running");
        health.put("version", "1.0.0");
        return health;
    }

    @GetMapping("/env")
    public Map<String, String> environmentVariables() {
        Map<String, String> env = new HashMap<>();
        
        // Database related environment variables
        env.put("MYSQL_URL", System.getenv("MYSQL_URL"));
        env.put("DATABASE_URL", System.getenv("DATABASE_URL"));
        env.put("PROD_DB_HOST", System.getenv("PROD_DB_HOST"));
        env.put("PROD_DB_PORT", System.getenv("PROD_DB_PORT"));
        env.put("PROD_DB_NAME", System.getenv("PROD_DB_NAME"));
        env.put("MYSQLUSER", System.getenv("MYSQLUSER"));
        env.put("DB_USERNAME", System.getenv("DB_USERNAME"));
        env.put("PROD_DB_USERNAME", System.getenv("PROD_DB_USERNAME"));
        env.put("MYSQLPASSWORD", System.getenv("MYSQLPASSWORD") != null ? "***" : null);
        env.put("DB_PASSWORD", System.getenv("DB_PASSWORD") != null ? "***" : null);
        env.put("PROD_DB_PASSWORD", System.getenv("PROD_DB_PASSWORD") != null ? "***" : null);
        
        // Application environment variables
        env.put("SPRING_PROFILES_ACTIVE", environment.getProperty("spring.profiles.active"));
        env.put("PORT", System.getenv("PORT"));
        
        // Email related environment variables
        env.put("MAIL_USERNAME", System.getenv("MAIL_USERNAME"));
        env.put("MAIL_PASSWORD", System.getenv("MAIL_PASSWORD") != null ? "***" : null);
        env.put("APP_EMAIL_FROM", System.getenv("APP_EMAIL_FROM"));
        env.put("APP_URL", System.getenv("APP_URL"));
        
        // Add database connection status
        String mysqlUrl = System.getenv("MYSQL_URL") != null ? System.getenv("MYSQL_URL") : 
                         System.getenv("DATABASE_URL") != null ? System.getenv("DATABASE_URL") :
                         System.getenv("PROD_DB_HOST") != null ? 
                             "jdbc:mysql://" + System.getenv("PROD_DB_HOST") + ":" + 
                             (System.getenv("PROD_DB_PORT") != null ? System.getenv("PROD_DB_PORT") : "3306") + 
                             "/" + (System.getenv("PROD_DB_NAME") != null ? System.getenv("PROD_DB_NAME") : "railway") : null;
        
        String mysqlUser = System.getenv("MYSQLUSER") != null ? System.getenv("MYSQLUSER") :
                          System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") :
                          System.getenv("PROD_DB_USERNAME") != null ? System.getenv("PROD_DB_USERNAME") : "root";
        
        String mysqlPassword = System.getenv("MYSQLPASSWORD") != null ? System.getenv("MYSQLPASSWORD") :
                              System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") :
                              System.getenv("PROD_DB_PASSWORD") != null ? System.getenv("PROD_DB_PASSWORD") : null;
        
        env.put("DB_CONNECTION_STATUS", (mysqlUrl != null && mysqlUser != null && mysqlPassword != null) ? "READY" : "MISSING_VARIABLES");
        env.put("DB_URL_DETECTED", mysqlUrl != null ? "YES" : "NO");
        env.put("DB_USER_DETECTED", mysqlUser != null ? "YES" : "NO");
        env.put("DB_PASSWORD_DETECTED", mysqlPassword != null ? "YES" : "NO");
        
        return env;
    }
} 