package com.projetofef.resources;

import com.projetofef.domains.dtos.TransferenciaDTO;
import com.projetofef.services.TransferenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/transferencia")
public class TransferenciaResource {

    private final TransferenciaService service;

    public TransferenciaResource(TransferenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<TransferenciaDTO>> list(
            @RequestParam(required = false) Integer contaBancariaId,
            @PageableDefault(size = 20, sort = "data") Pageable pageable) {

        Page<TransferenciaDTO> page;

        if (contaBancariaId != null) {
            page = service.findAllByContaBancaria(contaBancariaId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferenciaDTO>> listAll(
            @RequestParam(required = false) Integer contaBancariaId) {

        List<TransferenciaDTO> body;

        if (contaBancariaId != null) {
            body = service.findAllByContaBancaria(contaBancariaId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> findById(@PathVariable Integer id) {
        TransferenciaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(TransferenciaDTO.Update.class) TransferenciaDTO dto) {

        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<TransferenciaDTO> create(
            @RequestBody @Validated(TransferenciaDTO.Create.class) TransferenciaDTO dto) {

        TransferenciaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}