package com.jakimenko.sometest;

import com.jakimenko.sometest.repository.CountryRepo;
import com.jakimenko.sometest.repository.ICountryRepo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class PostgresSimpleTest {

    @Container
    public final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:11")// Создать контейнер из образа postgres:11
                    .withDatabaseName("app")
                    .withUsername("app")
                    .withPassword("app")
                    .withInitScript("db.sql");// Выполнить db.sql после запуска

    private ICountryRepo repository;

    @BeforeEach
    void setUp() {
        var hikariConfig = new HikariConfig();
        System.out.println("POSTGRESQL_CONTAINER.getJdbcUrl() = " + POSTGRESQL_CONTAINER.getJdbcUrl());
        hikariConfig.setJdbcUrl(POSTGRESQL_CONTAINER.getJdbcUrl());// Получить реальный jdbcUrl
        hikariConfig.setUsername(POSTGRESQL_CONTAINER.getUsername());// Получить username
        hikariConfig.setPassword(POSTGRESQL_CONTAINER.getPassword());// Получить password
        hikariConfig.setMaximumPoolSize(2);
        hikariConfig.setMinimumIdle(0);
        hikariConfig.setConnectionTimeout(75000);
        hikariConfig.setReadOnly(false);
        var dataSource = new HikariDataSource(hikariConfig);
        repository = new CountryRepo(dataSource);
    }

    @Test
    void findAllFrameworks_ReturnsFrameworksList() {
        // when
        var countries = repository.getCountries();
        System.out.println("countries = " + countries);

        assertEquals(3, countries.size());
    }

//    @Test
//    void persistFramework_ReturnsGeneratedId( ){
//        // given
//        var framework = new Framework("Testcontainers Java", "Java", "https://www.testcontainers.org/");
//
//        // when
//        var newId = repository.persistFramework(framework);
//
//        // then
//        assertEquals(4, newId);
//    }

}
