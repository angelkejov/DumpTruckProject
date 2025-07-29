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
    
    @Autowired
    private DataSource dataSource;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        
        // Application status
        healthStatus.put("status", "UP");
        healthStatus.put("timestamp", System.currentTimeMillis());
        
        // Database status
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                healthStatus.put("database", "UP");
            } else {
                healthStatus.put("database", "DOWN");
            }
        } catch (SQLException e) {
            healthStatus.put("database", "DOWN");
            healthStatus.put("database_error", e.getMessage());
        }
        
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
} 