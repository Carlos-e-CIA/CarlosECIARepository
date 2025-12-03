package com.projetofef.resources;

import com.projetofef.domains.dtos.CentroCustoDTO;
import com.projetofef.services.CentroCustoService;
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
@RequestMapping("/api/centroCusto")
public class CentroCustoResource {

    private final CentroCustoService service;

    public CentroCustoResource(CentroCustoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CentroCustoDTO>> list(
            @RequestParam(required = false) Integer usuarioId,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        Page<CentroCustoDTO> page;

        if (usuarioId != null) {
            page = service.findAllByUsuario(usuarioId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CentroCustoDTO>> listAll(
            @RequestParam(required = false) Integer usuarioId) {

        List<CentroCustoDTO> body;

        if (usuarioId != null) {
            body = service.findAllByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroCustoDTO> findById(@PathVariable Integer id) {
        CentroCustoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroCustoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(CentroCustoDTO.Update.class) CentroCustoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<CentroCustoDTO> create(
            @RequestBody @Validated(CentroCustoDTO.Create.class) CentroCustoDTO dto) {
        CentroCustoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}