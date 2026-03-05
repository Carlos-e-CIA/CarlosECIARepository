package com.projetofef.resources;

import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.services.LancamentoService;
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
@RequestMapping("/api/lancamento")
public class LancamentoResource {
    private final LancamentoService service;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoDTO>> list(
            @RequestParam(required = false) Integer contaBancariaId,
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) Integer entidadeId,
            @RequestParam(required = false) Integer centroCustoId,
            @RequestParam(required = false) Integer cartaoCreditoId,
            @PageableDefault(size = 20, sort = "descricao") Pageable pageable) {

        Page<LancamentoDTO> page;

        if (contaBancariaId != null && usuarioId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, centroCustoId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null && centroCustoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCusto(contaBancariaId, usuarioId, entidadeId, centroCustoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, centroCustoId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, entidadeId, centroCustoId, cartaoCreditoId, pageable);
        } else if (usuarioId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(usuarioId, entidadeId, centroCustoId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndEntidade(contaBancariaId, usuarioId, entidadeId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && centroCustoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndCentroCusto(contaBancariaId, usuarioId, centroCustoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndUsuarioAndCartaoCredito(contaBancariaId, usuarioId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && entidadeId != null && centroCustoId != null) {
            page = service.findAllByContaBancariaAndEntidadeAndCentroCusto(contaBancariaId, entidadeId, centroCustoId, pageable);
        } else if (contaBancariaId != null && entidadeId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndEntidadeAndCartaoCredito(contaBancariaId, entidadeId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndCentroCustoAndCartaoCredito(contaBancariaId, centroCustoId, cartaoCreditoId, pageable);
        } else if (usuarioId != null && entidadeId != null && centroCustoId != null) {
            page = service.findAllByUsuarioAndEntidadeAndCentroCusto(usuarioId, entidadeId, centroCustoId, pageable);
        } else if (usuarioId != null && entidadeId != null && cartaoCreditoId != null) {
            page = service.findAllByUsuarioAndEntidadeAndCartaoCredito(usuarioId, entidadeId, cartaoCreditoId, pageable);
        } else if (usuarioId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByUsuarioAndCentroCustoAndCartaoCredito(usuarioId, centroCustoId, cartaoCreditoId, pageable);
        } else if (entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByEntidadeAndCentroCustoAndCartaoCredito(entidadeId, centroCustoId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null && usuarioId != null) {
            page = service.findAllByContaBancariaAndUsuario(contaBancariaId, usuarioId, pageable);
        } else if (contaBancariaId != null && entidadeId != null) {
            page = service.findAllByContaBancariaAndEntidade(contaBancariaId, entidadeId, pageable);
        } else if (contaBancariaId != null && centroCustoId != null) {
            page = service.findAllByContaBancariaAndCentroCusto(contaBancariaId, centroCustoId, pageable);
        } else if (contaBancariaId != null && cartaoCreditoId != null) {
            page = service.findAllByContaBancariaAndCartaoCredito(contaBancariaId, cartaoCreditoId, pageable);
        } else if (usuarioId != null && entidadeId != null) {
            page = service.findAllByUsuarioAndEntidade(usuarioId, entidadeId, pageable);
        } else if (usuarioId != null && centroCustoId != null) {
            page = service.findAllByUsuarioAndCentroCusto(usuarioId, centroCustoId, pageable);
        } else if (usuarioId != null && cartaoCreditoId != null) {
            page = service.findAllByUsuarioAndCartaoCredito(usuarioId, cartaoCreditoId, pageable);
        } else if (entidadeId != null && centroCustoId != null) {
            page = service.findAllByEntidadeAndCentroCusto(entidadeId, centroCustoId, pageable);
        } else if (entidadeId != null && cartaoCreditoId != null) {
            page = service.findAllByEntidadeAndCartaoCredito(entidadeId, cartaoCreditoId, pageable);
        } else if (centroCustoId != null && cartaoCreditoId != null) {
            page = service.findAllByCentroCustoAndCartaoCredito(centroCustoId, cartaoCreditoId, pageable);
        } else if (contaBancariaId != null) {
            page = service.findAllByContaBancaria(contaBancariaId, pageable);
        } else if (usuarioId != null) {
            page = service.findAllByUsuario(usuarioId, pageable);
        } else if (entidadeId != null) {
            page = service.findAllByEntidade(entidadeId, pageable);
        } else if (centroCustoId != null) {
            page = service.findAllByCentroCusto(centroCustoId, pageable);
        } else if (cartaoCreditoId != null) {
            page = service.findAllByCartaoCredito(cartaoCreditoId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LancamentoDTO>> listAll(
            @RequestParam(required = false) Integer contaBancariaId,
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) Integer entidadeId,
            @RequestParam(required = false) Integer centroCustoId,
            @RequestParam(required = false) Integer cartaoCreditoId) {

        List<LancamentoDTO> body;

        if (contaBancariaId != null && usuarioId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, centroCustoId, cartaoCreditoId);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null && centroCustoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCusto(contaBancariaId, usuarioId, entidadeId, centroCustoId);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndEntidadeAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, cartaoCreditoId);
        } else if (contaBancariaId != null && usuarioId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, centroCustoId, cartaoCreditoId);
        } else if (contaBancariaId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, entidadeId, centroCustoId, cartaoCreditoId);
        } else if (usuarioId != null && entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(usuarioId, entidadeId, centroCustoId, cartaoCreditoId);
        } else if (contaBancariaId != null && usuarioId != null && entidadeId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndEntidade(contaBancariaId, usuarioId, entidadeId);
        } else if (contaBancariaId != null && usuarioId != null && centroCustoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndCentroCusto(contaBancariaId, usuarioId, centroCustoId);
        } else if (contaBancariaId != null && usuarioId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndUsuarioAndCartaoCredito(contaBancariaId, usuarioId, cartaoCreditoId);
        } else if (contaBancariaId != null && entidadeId != null && centroCustoId != null) {
            body = service.findAllByContaBancariaAndEntidadeAndCentroCusto(contaBancariaId, entidadeId, centroCustoId);
        } else if (contaBancariaId != null && entidadeId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndEntidadeAndCartaoCredito(contaBancariaId, entidadeId, cartaoCreditoId);
        } else if (contaBancariaId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndCentroCustoAndCartaoCredito(contaBancariaId, centroCustoId, cartaoCreditoId);
        } else if (usuarioId != null && entidadeId != null && centroCustoId != null) {
            body = service.findAllByUsuarioAndEntidadeAndCentroCusto(usuarioId, entidadeId, centroCustoId);
        } else if (usuarioId != null && entidadeId != null && cartaoCreditoId != null) {
            body = service.findAllByUsuarioAndEntidadeAndCartaoCredito(usuarioId, entidadeId, cartaoCreditoId);
        } else if (usuarioId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByUsuarioAndCentroCustoAndCartaoCredito(usuarioId, centroCustoId, cartaoCreditoId);
        } else if (entidadeId != null && centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByEntidadeAndCentroCustoAndCartaoCredito(entidadeId, centroCustoId, cartaoCreditoId);
        } else if (contaBancariaId != null && usuarioId != null) {
            body = service.findAllByContaBancariaAndUsuario(contaBancariaId, usuarioId);
        } else if (contaBancariaId != null && entidadeId != null) {
            body = service.findAllByContaBancariaAndEntidade(contaBancariaId, entidadeId);
        } else if (contaBancariaId != null && centroCustoId != null) {
            body = service.findAllByContaBancariaAndCentroCusto(contaBancariaId, centroCustoId);
        } else if (contaBancariaId != null && cartaoCreditoId != null) {
            body = service.findAllByContaBancariaAndCartaoCredito(contaBancariaId, cartaoCreditoId);
        } else if (usuarioId != null && entidadeId != null) {
            body = service.findAllByUsuarioAndEntidade(usuarioId, entidadeId);
        } else if (usuarioId != null && centroCustoId != null) {
            body = service.findAllByUsuarioAndCentroCusto(usuarioId, centroCustoId);
        } else if (usuarioId != null && cartaoCreditoId != null) {
            body = service.findAllByUsuarioAndCartaoCredito(usuarioId, cartaoCreditoId);
        } else if (entidadeId != null && centroCustoId != null) {
            body = service.findAllByEntidadeAndCentroCusto(entidadeId, centroCustoId);
        } else if (entidadeId != null && cartaoCreditoId != null) {
            body = service.findAllByEntidadeAndCartaoCredito(entidadeId, cartaoCreditoId);
        } else if (centroCustoId != null && cartaoCreditoId != null) {
            body = service.findAllByCentroCustoAndCartaoCredito(centroCustoId, cartaoCreditoId);
        } else if (contaBancariaId != null) {
            body = service.findAllByContaBancaria(contaBancariaId);
        } else if (usuarioId != null) {
            body = service.findAllByUsuario(usuarioId);
        } else if (entidadeId != null) {
            body = service.findAllByEntidade(entidadeId);
        } else if (centroCustoId != null) {
            body = service.findAllByCentroCusto(centroCustoId);
        } else if (cartaoCreditoId != null) {
            body = service.findAllByCartaoCredito(cartaoCreditoId);
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

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<LancamentoDTO> findByDescricao(@PathVariable String descricao) {
        LancamentoDTO dto = service.findByDescricao(descricao);
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