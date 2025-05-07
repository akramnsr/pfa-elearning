package com.elearning.rest;

import com.elearning.dto.ResultatDto;
import com.elearning.mapper.ResultatMapper;
import com.elearning.service.ResultatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/resultats")
public class ResultatRestController {

    private final ResultatService service;
    private final ResultatMapper mapper;

    public ResultatRestController(ResultatService service,
                                  ResultatMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    @PostMapping
    public ResponseEntity<ResultatDto> create(@Valid @RequestBody ResultatDto dto) {
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
    public ResponseEntity<Page<ResultatDto>> list(Pageable p) {
        Page<ResultatDto> page = service.findAll(p)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultatDto> getOne(@PathVariable Long id) {
        ResultatDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Résultat introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultatDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ResultatDto dto
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
