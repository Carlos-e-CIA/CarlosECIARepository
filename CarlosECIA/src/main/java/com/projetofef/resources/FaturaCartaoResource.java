package com.projetofef.resources;

import com.projetofef.domains.FaturaCartao;
import com.projetofef.domains.dtos.FaturaCartaoDTO;
import com.projetofef.domains.dtos.CartaoCreditoDTO;
import com.projetofef.services.FaturaCartaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/faturaCartao")
public class FaturaCartaoResource {
    private final FaturaCartaoService service;

    public FaturaCartaoResource(FaturaCartaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<FaturaCartaoDTO>> list(
            @RequestParam(required = false) Integer cartaoCreditoId,
            @PageableDefault(size = 20, sort = "valorTotal") Pageable pageable) {

        Page<FaturaCartaoDTO> page;

        if (cartaoCreditoId != null) {
            page = service.findAllByCartaoCredito(cartaoCreditoId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FaturaCartaoDTO>> listAll(
            @RequestParam(required = false) Integer cartaoCreditoId) {

        List<FaturaCartaoDTO> body;

        if (cartaoCreditoId != null) {
            body = service.findAllByCartaoCredito(cartaoCreditoId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturaCartaoDTO> findById(@PathVariable Integer id) {
        FaturaCartaoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/valorTotal/{valorTotal}")
    public ResponseEntity<FaturaCartaoDTO> findByValorTotal(@PathVariable BigDecimal valorTotal) {
        FaturaCartaoDTO dto = service.findByValorTotal(valorTotal);
        return ResponseEntity.ok(dto);
    }
}