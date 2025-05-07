package com.elearning.web;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import com.elearning.repository.FormationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class FormationRestControllerIntegrationTest {

    @Autowired private TestRestTemplate rest;
    @Autowired private FormationRepository repo;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/formations";
    }

    @LocalServerPort
    private int port;

    @Test
    void getOneReturnsDto() {
        Formation formation = new Formation("Java", "Cours Java", 40);
        formation = repo.save(formation);

        ResponseEntity<FormationDto> res = rest.getForEntity(
                baseUrl() + "/" + formation.getId(), FormationDto.class
        );

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().getTitle()).isEqualTo("Java");
    }

    /*@Test
    void post_then_get_then_put_then_delete() {
        // CREATE
        FormationDto dto = new FormationDto(null, "Docker", "Conteneurs légers");
        ResponseEntity<FormationDto> resCreate =
                rest.postForEntity(baseUrl, dto, FormationDto.class);
        assertThat(resCreate.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        FormationDto created = resCreate.getBody();
        Long id = created.getId();

        // GET
        ResponseEntity<FormationDto> resGet =
                rest.getForEntity(baseUrl + "/" + id, FormationDto.class);
        assertThat(resGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resGet.getBody().getTitle()).isEqualTo("Docker");

        // UPDATE
        created.setDescription("Mise à jour Docker");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FormationDto> updateEntity = new HttpEntity<>(created, headers);
        ResponseEntity<FormationDto> resUpdate =
                rest.exchange(baseUrl + "/" + id, HttpMethod.PUT, updateEntity, FormationDto.class);
        assertThat(resUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resUpdate.getBody().getDescription()).isEqualTo("Mise à jour Docker");

        // DELETE
        ResponseEntity<Void> resDelete =
                rest.exchange(baseUrl + "/" + id, HttpMethod.DELETE, null, Void.class);
        assertThat(resDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // GET Not Found
        ResponseEntity<ApiRestExceptionHandler.ErrorDto> resNotFound =
                rest.getForEntity(baseUrl + "/" + id, ApiRestExceptionHandler.ErrorDto.class);
        assertThat(resNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resNotFound.getBody()).isNotNull();
        assertThat(resNotFound.getBody().getCode()).isEqualTo("NOT_FOUND");
    }*/
}
