// src/test/java/com/elearning/web/QuizRestControllerTest.java
package com.elearning.web;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizMapper;
import com.elearning.model.Quiz;
import com.elearning.rest.QuizRestController;
import com.elearning.service.QuizService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(QuizRestController.class)
class QuizRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuizService service;

    @MockBean
    private QuizMapper mapper;

    @Test
    @DisplayName("GET /api/quizzes renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        // 1) DTO que le mapper devra retourner
        QuizDto dto = new QuizDto(42L, "Mon super quiz", "Desc");
        // 2) page d'entités que le service renverra
        Quiz entity = new Quiz("Mon super quiz", "Desc", 5, 10, new Date());
        entity.setId(42L);
        Page<Quiz> pageEntities = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);
        given(service.findAll(any(Pageable.class))).willReturn(pageEntities);
        given(mapper.toDto(any(Quiz.class))).willReturn(dto);

        // 3) appel GET et assertions JSON
        mvc.perform(get("/api/quizzes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",      hasSize(1)))
                .andExpect(jsonPath("$.content[0].titre").value(dto.getTitre()));
    }

    @Test
    @DisplayName("GET /api/quizzes/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        QuizDto dto = new QuizDto(7L, "Quiz #7", "La description");
        Quiz entity = new Quiz("Quiz #7", "La description", 3, 15, new Date());
        entity.setId(7L);
        given(service.findById(7L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/quizzes/{id}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.titre").value(dto.getTitre()));
    }
}
