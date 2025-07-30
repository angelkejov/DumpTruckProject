package com.shteotkacha.config;

import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;

@Configuration
@Profile("railway")
@EnableAutoConfiguration(exclude = {
    SystemMetricsAutoConfiguration.class
})
public class ActuatorConfig {
    
    // This configuration will disable problematic metrics in Railway environment
    // The application-railway.properties already disables most metrics
    // This is a backup configuration
} 