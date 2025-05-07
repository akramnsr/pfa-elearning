package com.elearning.rest;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizMapper;
import com.elearning.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final QuizService service;
    private final QuizMapper mapper;

    public QuizRestController(QuizService service,
                              QuizMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    @PostMapping
    public ResponseEntity<QuizDto> create(@Valid @RequestBody QuizDto dto) {
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
    public ResponseEntity<Page<QuizDto>> list(Pageable p) {
        Page<QuizDto> page = service.findAll(p)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getOne(@PathVariable Long id) {
        QuizDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Quiz introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> update(
            @PathVariable Long id,
            @Valid @RequestBody QuizDto dto
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
