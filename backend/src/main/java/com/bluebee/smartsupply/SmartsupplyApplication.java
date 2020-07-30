package com.bluebee.smartsupply;

import com.bluebee.smartsupply.config.AppConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class
})
public class SmartsupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartsupplyApplication.class, args);
	}



}
