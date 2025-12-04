package com.projetofef.resources;

import com.projetofef.domains.Lancamento;
import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.domains.dtos.PagamentoDTO;
import com.projetofef.services.LancamentoService;
import com.projetofef.services.PagamentoService;
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
@RequestMapping("/api/lancamento")
public class LancamentoResource {
    private final LancamentoService service;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoDTO>> list(
            @RequestParam(required = false) Integer usuario,
            @RequestParam(required = false) Integer entidade,
            @RequestParam(required = false) Integer centroCusto,
            @RequestParam(required = false) Integer contaBancaria,
            @RequestParam(required = false) Integer cartaoCredito,
            @PageableDefault(size = 300, sort = "descricao") Pageable pageable) {

        Page<LancamentoDTO> page;

        if (usuario != null && entidade != null && centroCusto != null && contaBancaria != null && cartaoCredito != null) {
            page = service.findAllByUsuarioAndEntidadeAndCentroCustoAndContaBancariaAndCartaoCredito(usuario, entidade, centroCusto, contaBancaria, cartaoCredito, pageable);
        } else if (usuario != null) {
            page = service.findAllByUsuario(usuario, pageable);
        } else if (entidade != null) {
            page = service.findAllByEntidade(entidade, pageable);
        } else if(centroCusto != null) {
            page = service.findAllByCentroCusto(centroCusto, pageable);
        } else if(contaBancaria != null) {
            page = service.findAllByContaBancaria(contaBancaria, pageable);
        } else if(cartaoCredito != null) {
            page = service.findAllByCartaoCredito(cartaoCredito, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LancamentoDTO>> listAll(
            @RequestParam(required = false) Integer usuario,
            @RequestParam(required = false) Integer entidade,
            @RequestParam(required = false) Integer centroCusto,
            @RequestParam(required = false) Integer contaBancaria,
            @RequestParam(required = false) Integer cartaoCredito
            ) {

        List<LancamentoDTO> body;

        if (usuario != null && entidade != null && centroCusto != null && contaBancaria != null && cartaoCredito != null) {
            body = service.findAllByUsuarioAndEntidadeAndCentroCustoAndContaBancariaAndCartaoCredito(usuario, entidade, centroCusto, contaBancaria, cartaoCredito);
        } else if (usuario != null) {
            body = service.findAllByUsuario(usuario);
        } else if (entidade != null) {
            body = service.findAllByEntidade(entidade);
        } else if(centroCusto != null) {
            body = service.findAllByCentroCusto(centroCusto);
        } else if(contaBancaria != null) {
            body = service.findAllByContaBancaria(contaBancaria);
        } else if(cartaoCredito != null) {
            body = service.findAllByCartaoCredito(cartaoCredito);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> findById(@PathVariable Integer id) {
        LancamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(LancamentoDTO.Update.class) LancamentoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<LancamentoDTO> create(
            @RequestBody @Validated(LancamentoDTO.Create.class) LancamentoDTO dto) {
        LancamentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}

