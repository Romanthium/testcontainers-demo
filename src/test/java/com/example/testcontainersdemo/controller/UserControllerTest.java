package com.example.testcontainersdemo.controller;

import com.example.testcontainersdemo.config.DatabaseTestConfiguration;
import com.example.testcontainersdemo.model.User;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DatabaseTestConfiguration.class)
@Testcontainers
public class UserControllerTest {

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void tearDown() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }

    @Test
    public void testCreateUser() {
        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("testuser@gmail.com")
                .password("testpass")
                .build();

        User response = restTemplate.postForObject("http://localhost:" + port + "/users", user, User.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("Test");
        assertThat(response.getLastName()).isEqualTo("User");
        assertThat(response.getEmail()).isEqualTo("testuser@gmail.com");

    }
}