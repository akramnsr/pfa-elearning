// src/test/java/com/elearning/web/QuizControllerIntegrationTest.java
package com.elearning.web;

import com.elearning.model.Quiz;
import com.elearning.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc(addFilters = false)  // DÉSACTIVE LA SÉCURITÉ SPRING
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuizControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    QuizRepository repo;

    @Autowired
    TestRestTemplate rest;

    @BeforeEach
    void setup() {
        repo.deleteAll();
        repo.save(new Quiz("Un titre", "Desc", 3, 15, new Date()));
    }

    @Test
    void listPageRenders() {
        String url = "http://localhost:" + port + "/quizzes";
        String html = rest.getForObject(url, String.class);
        assertThat(html).contains("Un titre");
    }
}
