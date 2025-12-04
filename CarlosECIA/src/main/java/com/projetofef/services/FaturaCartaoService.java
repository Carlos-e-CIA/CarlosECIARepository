package com.projetofef.services;

import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.FaturaCartao;
import com.projetofef.domains.Pagamento;
import com.projetofef.domains.dtos.FaturaCartaoDTO;
import com.projetofef.domains.dtos.PagamentoDTO;
import com.projetofef.mappers.FaturaCartaoMapper;
import com.projetofef.mappers.PagamentoMapper;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.repositories.FaturaCartaoRepository;
import com.projetofef.repositories.PagamentoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public Page<FaturaCartaoDTO> findAllByCartaoCredito(Integer cartaoCredito, Pageable pageable) {
        if (cartaoCredito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão de crédito é obrigatório");
        }

        if (!cartaoCredito.existsById(cartaoCredito)) {
            throw new ObjectNotFoundException("Cartão de crédito " + cartaoCredito + "não encontrado");
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

        Page<FaturaCartao> page = faturaCartaoRepo.findByCartaoCredito_Id(cartaoCredito, effective);
        return FaturaCartaoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<FaturaCartaoDTO> findAllByCartaoCredito(Integer cartaoCredito) {
        return findAllByCartaoCredito(cartaoCredito, Pageable.unpaged()).getContent();
    }


    @Transactional(readOnly = true)
    public FaturaCartaoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id da Fatura cartão é obrigatório");
        }

        return faturaCartaoRepo.findById(id)
                .map(FaturaCartaoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura Cartão não encontrado: id=" + id));
    }


    @Transactional
    public FaturaCartaoDTO create(FaturaCartaoDTO faturaCartaoDTO) {

        if (faturaCartaoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da fatura são obrigatórios");
        }

        Integer cartaoCredito = faturaCartaoDTO.getCartaoCredito();

        if (cartaoCredito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A fatura é obrigatória");
        }

        CartaoCredito cartaoCredito1 = cartaoCreditoRepo.findById(cartaoCredito)
                .orElseThrow(() -> new ObjectNotFoundException("Cartão não encontrado: id=" + cartaoCredito));


        faturaCartaoDTO.setId(null);
        FaturaCartao faturaCartao;
        try{
            faturaCartao = FaturaCartaoMapper.toEntity(faturaCartaoDTO, cartaoCredito1);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return FaturaCartaoMapper.toDto(faturaCartaoRepo.save(faturaCartao));
    }

    @Transactional
    public FaturaCartaoDTO update(Integer id, FaturaCartaoDTO faturaCartaoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (faturaCartaoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da fatura são obrigatórios");
        }

        FaturaCartao faturaCartao = faturaCartaoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura não encontrado: id=" + id));

        Integer cartaoCredito = faturaCartaoDTO.getCartaoCredito();

        if (cartaoCredito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "o cartão de crédito é obrigatório");
        }

        CartaoCredito cartaoCredito1 = cartaoCreditoRepo.findById(cartaoCredito)
                .orElseThrow(() -> new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + cartaoCredito));


        FaturaCartaoMapper.copyToEntity(faturaCartaoDTO, faturaCartao, cartaoCredito1);

        return FaturaCartaoMapper.toDto(faturaCartaoRepo.save(faturaCartao));
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        FaturaCartao faturaCartao = faturaCartaoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura não encontrada: id=" + id));


        faturaCartaoRepo.delete(faturaCartao);
    }
}
