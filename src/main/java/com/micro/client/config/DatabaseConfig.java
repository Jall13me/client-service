package com.micro.client.config;

import com.micro.client.config.database.DatabaseStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Value("${database.strategy}")
    private String strategyName;

    @Value("${database.host:localhost}")
    private String host;

    @Value("${database.port:}")
    private String port;

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Autowired
    private ApplicationContext context;

    private DatabaseStrategy strategy;

    @PostConstruct
    public void init() {
        strategy = context.getBean(strategyName, DatabaseStrategy.class);
        log.info("Database Strategy: {}", strategyName.toUpperCase());
        log.info("Driver: {}", strategy.getDriverClassName());
        log.info("Dialect: {}", strategy.getHibernateDialect());
    }

    @Bean
    public DataSource dataSource() {
        String jdbcUrl = strategy.buildUrl(host, port, databaseName);

        log.info("JDBC URL: {}", jdbcUrl);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(strategy.getDriverClassName());
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

    @Bean
    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", strategy.getHibernateDialect());
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        return props;
    }
}