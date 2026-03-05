package com.projetofef.services;

import com.projetofef.domains.FaturaCartao;
import com.projetofef.domains.dtos.FaturaCartaoDTO;
import com.projetofef.mappers.FaturaCartaoMapper;
import com.projetofef.repositories.FaturaCartaoRepository;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FaturaCartaoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final FaturaCartaoRepository faturaCartaoRepo;
    private final CartaoCreditoRepository cartaoCreditoRepo;

    public FaturaCartaoService(FaturaCartaoRepository faturaCartaoRepo, CartaoCreditoRepository cartaoCreditoRepo) {
        this.faturaCartaoRepo = faturaCartaoRepo;
        this.cartaoCreditoRepo = cartaoCreditoRepo;
    }

    @Transactional(readOnly = true)
    public List<FaturaCartaoDTO> findAll() {
        return FaturaCartaoMapper.toDtoList(faturaCartaoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<FaturaCartaoDTO> findAll(Pageable pageable) {
        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        }
        else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<FaturaCartao> page = faturaCartaoRepo.findAll(effective);
        return FaturaCartaoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<FaturaCartaoDTO> findAllByCartaoCredito(Integer cartaoCreditoId, Pageable pageable) {
        if (cartaoCreditoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cartaoCreditoId é obrigatório");
        }

        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        }
        else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<FaturaCartao> page = faturaCartaoRepo.findByCartaoCredito_Id(cartaoCreditoId, effective);
        return FaturaCartaoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<FaturaCartaoDTO> findAllByCartaoCredito(Integer cartaoCreditoId) {
        return findAllByCartaoCredito(cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public FaturaCartaoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de FaturaCartao é obrigatório");
        }

        return faturaCartaoRepo.findById(id)
                .map(FaturaCartaoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("FaturaCartao não encontrada: id = " + id));
    }

    @Transactional(readOnly = true)
    public FaturaCartaoDTO findByValorTotal(BigDecimal valorTotal) {
        if (valorTotal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ValorTotal da FaturaCartao é obrigatório");
        }

        return faturaCartaoRepo.findByValorTotal(valorTotal)
                .map(FaturaCartaoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("FaturaCartao não encontrada: ValorTotal = " + valorTotal));
    }
}