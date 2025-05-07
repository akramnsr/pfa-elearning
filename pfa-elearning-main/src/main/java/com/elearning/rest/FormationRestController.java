package com.elearning.rest;

import com.elearning.dto.FormationDto;
import com.elearning.mapper.FormationMapper;
import com.elearning.service.FormationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formations")
public class FormationRestController {

    private final FormationService service;
    private final FormationMapper mapper;

    public FormationRestController(FormationService service,
                                   FormationMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    // Méthode de récupération paginée des formations (consultation uniquement)
    @GetMapping
    public ResponseEntity<Page<FormationDto>> list(Pageable pageable) {
        var page = service.findAll(pageable).map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    // Méthode pour récupérer une seule formation par ID (consultation uniquement)
    @GetMapping("/{id}")
    public ResponseEntity<FormationDto> getOne(@PathVariable Long id) {
        var dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Formation introuvable pour l'ID " + id
                        )
                );
        return ResponseEntity.ok(dto);
    }

    // SUPPRESSION des méthodes POST, PUT et DELETE pour assurer une consultation seule.
}
