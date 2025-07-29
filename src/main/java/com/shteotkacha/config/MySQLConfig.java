package com.shteotkacha.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@Profile("railway")
public class MySQLConfig {

    private static final Logger logger = LoggerFactory.getLogger(MySQLConfig.class);

    @Bean
    public DataSource dataSource() {
        String mysqlUrl = System.getenv("MYSQL_URL");
        String mysqlUser = System.getenv("MYSQLUSER");
        String mysqlPassword = System.getenv("MYSQLPASSWORD");
        
        logger.info("Configuring MySQL DataSource");
        logger.info("MYSQL_URL: {}", mysqlUrl != null ? mysqlUrl.substring(0, Math.min(mysqlUrl.length(), 20)) + "..." : "NULL");
        logger.info("MYSQLUSER: {}", mysqlUser);
        logger.info("MYSQLPASSWORD: {}", mysqlPassword != null ? "***" : "NULL");
        
        if (mysqlUrl == null || mysqlUser == null || mysqlPassword == null) {
            throw new RuntimeException("MySQL environment variables are missing! MYSQL_URL, MYSQLUSER, and MYSQLPASSWORD must be set.");
        }
        
        return DataSourceBuilder.create()
                .url(mysqlUrl)
                .username(mysqlUser)
                .password(mysqlPassword)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
} 