package com.shteotkacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;

@SpringBootApplication(exclude = {
    SystemMetricsAutoConfiguration.class
})
public class DumpTruckServicesApplication {

    public static void main(String[] args) {
        // Force rebuild to remove H2 dependency
        SpringApplication.run(DumpTruckServicesApplication.class, args);
    }
} 