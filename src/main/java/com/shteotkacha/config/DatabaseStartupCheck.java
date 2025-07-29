package com.shteotkacha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile("railway")
public class DatabaseStartupCheck implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseStartupCheck.class);
    private final DataSource dataSource;

    public DatabaseStartupCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== DATABASE CONNECTION CHECK ===");
        logger.info("Database URL: {}", dataSource.getConnection().getMetaData().getURL());
        logger.info("Database Product: {}", dataSource.getConnection().getMetaData().getDatabaseProductName());
        logger.info("Database Version: {}", dataSource.getConnection().getMetaData().getDatabaseProductVersion());
        logger.info("=== END DATABASE CHECK ===");
    }
} 