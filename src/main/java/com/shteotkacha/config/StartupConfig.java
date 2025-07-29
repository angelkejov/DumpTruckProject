package com.shteotkacha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("railway")
public class StartupConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== APPLICATION STARTUP COMPLETE ===");
        logger.info("Application is ready to accept requests");
        logger.info("Health check available at: /api/health");
        logger.info("Environment: {}", System.getenv("SPRING_PROFILES_ACTIVE"));
        logger.info("Port: {}", System.getenv("PORT"));
        logger.info("=== END STARTUP ===");
    }
} 