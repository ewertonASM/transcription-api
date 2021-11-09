package br.com.inatel.transcriptGatewayApi.configure;


import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
// @ConfigurationProperties(prefix = "spring.datasource")
// public class DataSourceConfiguration extends HikariConfig {

//         @Bean
//         public DataSource dataSource()  {
//             return new HikariDataSource(this);
//         }

// }