package com.elearning.rest;

import com.elearning.dto.RapportEtuDto;
import com.elearning.mapper.RapportEtuMapper;
import com.elearning.service.RapportEtuService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/rapports")
public class RapportEtuRestController {

    private final RapportEtuService service;
    private final RapportEtuMapper mapper;

    public RapportEtuRestController(RapportEtuService service,
                                    RapportEtuMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    @PostMapping
    public ResponseEntity<RapportEtuDto> create(@Valid @RequestBody RapportEtuDto dto) {
        var ent   = mapper.toEntity(dto);
        var saved = service.save(ent);
        var out   = mapper.toDto(saved);
        URI loc = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(out.getId())
                .toUri();
        return ResponseEntity.created(loc).body(out);
    }

    @GetMapping
    public ResponseEntity<Page<RapportEtuDto>> list(Pageable p) {
        Page<RapportEtuDto> page = service.findAll(p)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportEtuDto> getOne(@PathVariable Long id) {
        RapportEtuDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rapport introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RapportEtuDto> update(
            @PathVariable Long id,
            @Valid @RequestBody RapportEtuDto dto
    ) {
        dto.setId(id);
        var upd = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDto(upd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
