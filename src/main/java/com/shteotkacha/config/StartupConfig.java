package com.shteotkacha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("railway")
public class StartupConfig {

    private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("🚀 Application started successfully in Railway environment");
        logger.info("📊 Health check available at: /api/health");
        logger.info("🔍 Environment variables available at: /api/env");
        logger.info("🌐 Application ready to serve requests");
        logger.info("🔧 Database status: " + (System.getenv("MYSQL_URL") != null ? "MySQL configured" : "Using H2 fallback"));
    }
} 