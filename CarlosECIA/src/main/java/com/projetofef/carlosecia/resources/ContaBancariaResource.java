package com.projetofef.carlosecia.resources;

import com.projetofef.carlosecia.domains.dtos.ContaBancariaDTO;
import com.projetofef.carlosecia.services.ContaBancariaService;
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
@RequestMapping("/api/contaBancaria")
public class ContaBancariaResource {
    private final ContaBancariaService service;

    public ContaBancariaResource(ContaBancariaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ContaBancariaDTO>> list(
            @RequestParam(required = false) Integer usuarioId,
            @PageableDefault(size = 20, sort = "numero") Pageable pageable) {

        Page<ContaBancariaDTO> page;

        if (usuarioId != null) {
            page = service.findAllByUsuario(usuarioId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContaBancariaDTO>> listAll(
            @RequestParam(required = false) Integer usuarioId {

        List<ContaBancariaDTO> body;

        if (usuarioId != null) {
            body = service.findAllByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> findById(@PathVariable Integer id) {
        ContaBancariaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<ContaBancariaDTO> findByNumero(@PathVariable Integer numero) {
        ContaBancariaDTO dto = service.findByNumero(numero);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(ContaBancariaDTO.Update.class) ContaBancariaDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<ContaBancariaDTO> create(
            @RequestBody @Validated(ContaBancariaDTO.Create.class) ContaBancariaDTO dto) {
        ContaBancariaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
