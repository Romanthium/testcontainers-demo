package com.example.testcontainersdemo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class DatabaseTestConfiguration {
    @Bean
    public PostgreSQLContainer<?> mySQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");

        container.start();

        return container;
    }
}
