package com.shteotkacha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@Configuration
@Profile("railway")
public class MySQLConfig {

    private static final Logger logger = LoggerFactory.getLogger(MySQLConfig.class);
    
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        // Try multiple possible environment variable names for Railway compatibility
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
        
        logger.info("Configuring MySQL DataSource");
        logger.info("MYSQL_URL: {}", mysqlUrl != null ? mysqlUrl.substring(0, Math.min(mysqlUrl.length(), 20)) + "..." : "NULL");
        logger.info("MYSQLUSER: {}", mysqlUser);
        logger.info("MYSQLPASSWORD: {}", mysqlPassword != null ? "***" : "NULL");
        
        // Log all relevant environment variables for debugging
        logger.info("Environment variables check:");
        logger.info("MYSQL_URL: {}", System.getenv("MYSQL_URL"));
        logger.info("DATABASE_URL: {}", System.getenv("DATABASE_URL"));
        logger.info("PROD_DB_HOST: {}", System.getenv("PROD_DB_HOST"));
        logger.info("PROD_DB_PORT: {}", System.getenv("PROD_DB_PORT"));
        logger.info("PROD_DB_NAME: {}", System.getenv("PROD_DB_NAME"));
        logger.info("MYSQLUSER: {}", System.getenv("MYSQLUSER"));
        logger.info("DB_USERNAME: {}", System.getenv("DB_USERNAME"));
        logger.info("PROD_DB_USERNAME: {}", System.getenv("PROD_DB_USERNAME"));
        logger.info("MYSQLPASSWORD: {}", System.getenv("MYSQLPASSWORD") != null ? "***" : "NULL");
        logger.info("DB_PASSWORD: {}", System.getenv("DB_PASSWORD") != null ? "***" : "NULL");
        logger.info("PROD_DB_PASSWORD: {}", System.getenv("PROD_DB_PASSWORD") != null ? "***" : "NULL");
        
        if (mysqlUrl == null || mysqlUser == null || mysqlPassword == null) {
            logger.error("MySQL environment variables are missing! Application cannot start without database connection.");
            logger.error("Please add MySQL database service to Railway project.");
            logger.error("Expected variables: MYSQL_URL/MYSQLUSER/MYSQLPASSWORD or DATABASE_URL/DB_USERNAME/DB_PASSWORD or PROD_DB_* variables");
            
            // Throw an exception to prevent startup without proper database
            throw new RuntimeException("MySQL database connection is required. Please configure database environment variables.");
        }
        
        return DataSourceBuilder.create()
                .url(mysqlUrl)
                .username(mysqlUser)
                .password(mysqlPassword)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
    
    @Bean
    public org.hibernate.dialect.Dialect hibernateDialect() {
        logger.info("Using MySQL dialect for Railway deployment");
        return new org.hibernate.dialect.MySQLDialect();
    }
} 