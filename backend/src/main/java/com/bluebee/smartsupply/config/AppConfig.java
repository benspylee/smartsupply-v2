package com.bluebee.smartsupply.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@Component
public class AppConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Bean
    public DataSource dataSource(){
        BasicDataSource bs=new BasicDataSource();
        bs.setUrl(url);
        bs.setDriverClassName(driver);
        bs.setUsername(username);
        bs.setPassword(password);
        return bs;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
