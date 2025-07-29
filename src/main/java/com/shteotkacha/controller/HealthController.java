package com.shteotkacha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        
        // Application status - always return UP if endpoint is reachable
        healthStatus.put("status", "UP");
        healthStatus.put("timestamp", System.currentTimeMillis());
        healthStatus.put("message", "Application is running");
        
        // Memory status
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memory = new HashMap<>();
        memory.put("total", runtime.totalMemory());
        memory.put("free", runtime.freeMemory());
        memory.put("used", runtime.totalMemory() - runtime.freeMemory());
        memory.put("max", runtime.maxMemory());
        healthStatus.put("memory", memory);
        
        return ResponseEntity.ok(healthStatus);
    }
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
    
    @GetMapping("/env")
    public ResponseEntity<Map<String, String>> environmentCheck() {
        Map<String, String> env = new HashMap<>();
        env.put("MYSQLHOST", System.getenv("MYSQLHOST"));
        env.put("MYSQLPORT", System.getenv("MYSQLPORT"));
        env.put("MYSQLUSER", System.getenv("MYSQLUSER"));
        env.put("MYSQLPASSWORD", System.getenv("MYSQLPASSWORD") != null ? "***" : null);
        env.put("MYSQLDATABASE", System.getenv("MYSQLDATABASE"));
        env.put("MYSQL_URL", System.getenv("MYSQL_URL"));
        env.put("SPRING_PROFILES_ACTIVE", System.getenv("SPRING_PROFILES_ACTIVE"));
        env.put("DATABASE_URL", System.getenv("DATABASE_URL"));
        env.put("ALL_ENV_VARS", String.join(", ", System.getenv().keySet().stream()
                .filter(key -> key.contains("MYSQL") || key.contains("DATABASE"))
                .collect(java.util.stream.Collectors.toList())));
        return ResponseEntity.ok(env);
    }
} 