// src/test/java/com/elearning/web/ResultatRestControllerTest.java
package com.elearning.web;

import com.elearning.dto.ResultatDto;
import com.elearning.mapper.ResultatMapper;
import com.elearning.model.Resultat;
import com.elearning.rest.ResultatRestController;
import com.elearning.service.ResultatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)  // DÉSACTIVE LA SÉCURITÉ SPRING
@WebMvcTest(ResultatRestController.class)
class ResultatRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ResultatService service;

    @MockBean
    private ResultatMapper mapper;

    @Test
    @DisplayName("GET /api/resultats renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        ResultatDto dto    = new ResultatDto(10L, 15.0);
        Resultat entity    = new Resultat();
        entity.setId(10);

        Page<Resultat> page = new PageImpl<>(
                List.of(entity),
                PageRequest.of(0, 10, Sort.by("id")),
                1
        );

        given(service.findAll(any(Pageable.class))).willReturn(page);
        given(mapper.toDto(any(Resultat.class))).willReturn(dto);

        mvc.perform(get("/api/resultats")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].score").value(dto.getScore()));
    }

    @Test
    @DisplayName("GET /api/resultats/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        ResultatDto dto = new ResultatDto(7L, 9.5);
        Resultat entity = new Resultat();
        entity.setId(7);

        given(service.findById(7L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/resultats/{id}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.score").value(dto.getScore()));
    }
}
