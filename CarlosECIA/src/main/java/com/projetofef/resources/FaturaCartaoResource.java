package com.projetofef.resources;

import com.projetofef.domains.dtos.FaturaCartaoDTO;
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
@RequestMapping("/api/faturacartao")
public class FaturaCartaoResource {
    private final FaturaCartaoService service;

    public FaturaCartaoResource(FaturaCartaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<FaturaCartaoDTO>> list(
            @RequestParam(required = false) Integer cartaoCredito,
            @PageableDefault(size = 20, sort = "cartaoCredito") Pageable pageable) {

        Page<FaturaCartaoDTO> page;

        if (cartaoCredito != null) {
            page = service.findAllByCartaoCredito(cartaoCredito,  pageable);
        }  else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FaturaCartaoDTO>> listAll(
            @RequestParam(required = false) Integer cartaoCredito)
            {

        List<FaturaCartaoDTO> body;

        if (cartaoCredito != null) {
            body = service.findAllByCartaoCredito(cartaoCredito);
        }  else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturaCartaoDTO> findById(@PathVariable Integer id) {
        FaturaCartaoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaturaCartaoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(FaturaCartaoDTO.Update.class) FaturaCartaoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<FaturaCartaoDTO> create(
            @RequestBody @Validated(FaturaCartaoDTO.Create.class) FaturaCartaoDTO dto) {
        FaturaCartaoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
