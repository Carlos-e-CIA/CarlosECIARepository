package com.projetofef.services;

import com.projetofef.domains.Recebimento;
import com.projetofef.domains.Lancamento;
import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.dtos.RecebimentoDTO;
import com.projetofef.mappers.RecebimentoMapper;
import com.projetofef.repositories.*;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.function.Function;

@Service
public class RecebimentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final RecebimentoRepository recebimentoRepo;
    private final LancamentoRepository lancRepo;
    private final ContaBancariaRepository contaRepo;
    private final PagamentoRepository pagamentoRepo;
    private final MovimentoContaRepository movimentoRepo;

    public RecebimentoService(
            RecebimentoRepository recebimentoRepo,
            LancamentoRepository lancRepo,
            ContaBancariaRepository contaRepo,
            PagamentoRepository pagamentoRepo,
            MovimentoContaRepository movimentoRepo
    ) {
        this.recebimentoRepo = recebimentoRepo;
        this.lancRepo = lancRepo;
        this.contaRepo = contaRepo;
        this.pagamentoRepo = pagamentoRepo;
        this.movimentoRepo = movimentoRepo;

    }
    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAll() {
        return RecebimentoMapper.toDtoList(recebimentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAll(Pageable pageable) {
        Pageable effective = getEffectivePageable(pageable);
        return RecebimentoMapper.toDtoPage(recebimentoRepo.findAll(effective));
    }

    @Transactional(readOnly = true)
    public RecebimentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return recebimentoRepo.findById(id)
                .map(RecebimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: id=" + id));
    }

    @Transactional(readOnly = true)
    public RecebimentoDTO findByObservacao(String obs) {
        if (obs == null || obs.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "observacao é obrigatória");
        }

        String normalized = obs.trim();

        return recebimentoRepo.findByObservacao(normalized)
                .map(RecebimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: obs=" + normalized));
    }     @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByLancamento(Integer lancamentoId, Pageable pageable) {
        if (!lancRepo.existsById(lancamentoId)) {
            throw new ObjectNotFoundException("Lancamento não encontrado: id=" + lancamentoId);
        }
        Page<Recebimento> page = recebimentoRepo.findByLancamento_Id(lancamentoId, getEffectivePageable(pageable));
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
        if (!contaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria não encontrada: id=" + contaBancariaId);
        }
        Page<Recebimento> page = recebimentoRepo.findByContaBancariaId(contaBancariaId, getEffectivePageable(pageable));
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByLancamentoAndContaDestino(Integer lancamentoId, Integer contaId, Pageable pageable) {
        if (!lancRepo.existsById(lancamentoId)) {
            throw new ObjectNotFoundException("Lancamento não encontrado: id=" + lancamentoId);
        }
        if (!contaRepo.existsById(contaId)) {
            throw new ObjectNotFoundException("ContaDestino não encontrada: id=" + contaBancariaId);
        }

        Page<Recebimento> page = recebimentoRepo.findByLancamentoIdAndContaBancariaId(lancamentoId, contaBancariaId, getEffectivePageable(pageable));
        return RecebimentoMapper.toDtoPage(page);
    }
    @Transactional
    public RecebimentoDTO create(RecebimentoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados são obrigatórios");
        }

        dto.setId(null);

        Lancamento lanc = lancRepo.findById(dto.getLancamentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Lançamento não encontrado: id=" + dto.getLancamentoId()));

        ContaBancaria conta = contaRepo.findById(dto.getContaBancariaId())
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancaria não encontrada: id=" + dto.getContaBancariaId()));

        Recebimento entity;
        try {
            entity = RecebimentoMapper.toEntity(dto, lanc, conta);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return RecebimentoMapper.toDto(recebimentoRepo.save(entity));
    }
    @Transactional
    public RecebimentoDTO update(Integer id, RecebimentoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Recebimento entity = recebimentoRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Recebimento não encontrado: id=" + id));

        Lancamento lanc = lancRepo.findById(dto.getLancamentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Lançamento não encontrado: id=" + dto.getLancamentoId()));

        ContaBancaria conta = contaRepo.findById(dto.getContaBancariaId())
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancaria não encontrada: id=" + dto.getContaBancariaId()));

        RecebimentoMapper.copyToEntity(dto, entity, lanc, conta);

        return RecebimentoMapper.toDto(recebimentoRepo.save(entity));
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Recebimento entity = recebimentoRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Recebimento não encontrado: id=" + id));

        if (movimentoRepo.existsByRecebimento_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Já existe Movimento de Conta vinculado a esse recebimento: id=" + id);
        }

        recebimentoRepo.delete(entity);
    }
    private Pageable getEffectivePageable(Pageable pageable) {
        if (pageable == null || pageable.isUnpaged()) {
            return Pageable.unpaged();
        }
        return PageRequest.of(
                Math.max(0, pageable.getPageNumber()),
                Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                pageable.getSort()
        );
    }
}