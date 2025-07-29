package com.shteotkacha.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@Profile("railway")
public class DatabaseConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.datasource.url", havingValue = ".*mysql.*", matchIfMissing = false)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(System.getenv("MYSQL_URL"))
                .username(System.getenv("MYSQLUSER"))
                .password(System.getenv("MYSQLPASSWORD"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
} 