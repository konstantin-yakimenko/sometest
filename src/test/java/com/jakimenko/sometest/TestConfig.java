package com.jakimenko.sometest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestConfig.class);

    // запуск и остановка контейнера по lifecycle-событиями компонента (1)
    @Bean(initMethod = "start", destroyMethod = "stop")
    public JdbcDatabaseContainer<?> jdbcDatabaseContainer() {
        return new PostgreSQLContainer<>("postgres:11")
                .withDatabaseName("app")
                .withUsername("app")
                .withPassword("app")
                .withInitScript("db.sql")
                .waitingFor(Wait.forListeningPort());// ожидание доступности порта (2)
    }

    @Bean
    public DataSource dataSource(JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
        var hikariConfig = new HikariConfig();
        logger.info("JdbcUrl: {}", jdbcDatabaseContainer.getJdbcUrl());
        hikariConfig.setJdbcUrl(jdbcDatabaseContainer.getJdbcUrl());
        hikariConfig.setUsername(jdbcDatabaseContainer.getUsername());
        hikariConfig.setPassword(jdbcDatabaseContainer.getPassword());
        hikariConfig.setMaximumPoolSize(2);
        hikariConfig.setMinimumIdle(0);
        hikariConfig.setConnectionTimeout(75000);
        hikariConfig.setReadOnly(false);
        return new HikariDataSource(hikariConfig);
    }
}
