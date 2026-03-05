package com.projetofef.resources;

import com.projetofef.domains.dtos.EntidadeDTO;
import com.projetofef.services.EntidadeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/entidade")
public class EntidadeResource {
    private final EntidadeService service;

    public EntidadeResource(EntidadeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<EntidadeDTO>> list(
            @RequestParam(required = false) Integer usuarioId,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        Page<EntidadeDTO> page;

        if (usuarioId != null) {
            page = service.findAllByUsuario(usuarioId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EntidadeDTO>> listAll(
            @RequestParam(required = false) Integer usuarioId) {

        List<EntidadeDTO> body;

        if (usuarioId != null) {
            body = service.findAllByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadeDTO> findById(@PathVariable Integer id) {
        EntidadeDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<EntidadeDTO> findByDocumento(@PathVariable String documento) {
        EntidadeDTO dto = service.findByDocumento(documento);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadeDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(EntidadeDTO.Update.class) EntidadeDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<EntidadeDTO> create(
            @RequestBody @Validated(EntidadeDTO.Create.class) EntidadeDTO dto) {
        EntidadeDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}