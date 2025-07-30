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
            logger.warn("MySQL environment variables are missing! Using minimal H2 fallback.");
            
            // Return a minimal datasource to allow the app to start
            return DataSourceBuilder.create()
                    .url("jdbc:h2:mem:temp;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL")
                    .username("sa")
                    .password("")
                    .driverClassName("org.h2.Driver")
                    .build();
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
        String mysqlUrl = System.getenv("MYSQL_URL") != null ? System.getenv("MYSQL_URL") : 
                         System.getenv("DATABASE_URL") != null ? System.getenv("DATABASE_URL") :
                         System.getenv("PROD_DB_HOST") != null ? 
                             "jdbc:mysql://" + System.getenv("PROD_DB_HOST") + ":" + 
                             (System.getenv("PROD_DB_PORT") != null ? System.getenv("PROD_DB_PORT") : "3306") + 
                             "/" + (System.getenv("PROD_DB_NAME") != null ? System.getenv("PROD_DB_NAME") : "railway") : null;
        
        if (mysqlUrl != null && mysqlUrl.contains("mysql")) {
            logger.info("Using MySQL dialect for Railway deployment");
            return new org.hibernate.dialect.MySQLDialect();
        } else {
            logger.info("Using H2 dialect for fallback");
            return new org.hibernate.dialect.H2Dialect();
        }
    }
} 