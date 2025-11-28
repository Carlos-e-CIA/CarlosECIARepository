package com.projetofef.services;

import com.projetofef.repositories.PagamentoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.projetofef.carlosecia.domains.dtos.PagamentoDTO;
import com.projetofef.carlosecia.mappers.PagamentoMapper;
import com.projetofef.carlosecia.domains.Pagamento;
import java.util.List;


@Service
public class PagamentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final PagamentoRepository pagamentoRepo;
    private final ContaBancariaRepository contaOrigemRepo;
    private final LancamentoRepository lancamentoRepo;

    public PagamentoService(PagamentoRepository pagamentoRepo, ContaBancariaRepository contaOrigemRepo, LancamentoRepository lancamentoRepo) {
        this.pagamentoRepo = pagamentoRepo;
        this.contaOrigemRepo = contaOrigemRepo;
        this.lancamentoRepo = lancamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {
        return PagamentoMapper.toDtoList(pagamentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
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

        Page<Pagamento> page = pagamentoRepo.findAll(effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAllByAutor(Integer contaOrigem, Pageable pageable) {
        if (contaOrigem == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaOrigem é obrigatório");
        }

        if (!contaOrigemRepo.existsById(contaOrigem)) {
            throw new ObjectNotFoundException("Conta Origem " + contaOrigem + "não encontrado");
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

        Page<Pagamento> page = contaOrigem.findByContaBancaria_Id(contaOrigem, effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllByContaBancaria(Integer contaOrigem) {
        return findAllByAutor(contaOrigem, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAllByLancamento(Integer lancamentoId, Pageable pageable) {
        if (lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lancamentoId é obrigatório");
        }

        if (!contaOrigemRepo.existsById(lancamentoId)) {
            throw new ObjectNotFoundException("Lancamento id " + lancamentoId + "não encontrado");
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

        Page<Pagamento> page = pagamentoRepo.findByLancamento_Id(lancamentoId, effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllByLancamento(Integer lancamento) {
        return findAllByLancamento(lancamento, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAllByAutorAndLancamento(Integer contaOrigem, Integer lancamento, Pageable pageable) {
        if (!contaOrigemRepo.existsById(contaOrigem)) {
            throw new ObjectNotFoundException("Conta Bancaria " + contaOrigem + " não encontrado");
        }
        if (!lancamentoRepo.existsById(lancamento)) {
            throw new ObjectNotFoundException("Lancamento id " + lancamento + " não encontrada");
        }

        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        } else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<Pagamento> page = contaOrigemRepo.findByContaBancaria_IdAndLancamento_Id(contaOrigem, lancamento, effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllByContaBancariaAndLancamento(Integer contaOrigem, Integer lancamento) {
        return findAllByContaBancariaAndLancamento(contaOrigem, lancamento, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Pagamento é obrigatório");
        }

        return contaOrigemRepo.findById(id)
                .map(PagamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));
    }


    @Transactional
    public PagamentoDTO create(PagamentoDTO pagamentoDTO) {

        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Integer contaOrigem = pagamentoDTO.getContaOrigem();
        Integer lancamento = pagamentoDTO.getLancamento();

        if (contaOrigem == null || lancamento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A conta origem e o Lancamento são obrigatórios");
        }

        ContaBancaria contaOrigem = contaOrigemRepo.findById(contaOrigem)
                .orElseThrow(() -> new ObjectNotFoundException("Conta Bancaria não encontrado: id=" + contaOrigem));

        Lancamento lancamento = lancamentoRepo.findById(lancamento)
                .orElseThrow(() -> new ObjectNotFoundException("Lancamento não encontrada: id=" + lancamento));

        pagamentoDTO.setId(null);
        Pagamento pagamento;
        try{
            pagamento = PagamentoMapper.toEntity(pagamentoDTO, contaOrigem, lancamento);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return PagamentoMapper.toDto(contaOrigemRepo.save(pagamento));
    }

    @Transactional
    public PagamentoDTO update(Integer id, PagamentoDTO pagamentoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Pagamento pagamento = contaOrigemRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        Integer contaOrigem = pagamentoDTO.getContaOrigem();
        Integer lancamento = pagamentoDTO.getLancamento();

        if (contaOrigem == null || lancamento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A conta bancaria e o Lancamento são obrigatórios");
        }

        ContaBancaria contaOrigem = contaOrigemRepo.findById(contaOrigem)
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancaria não encontrado: id=" + contaOrigem));

        Lancamento lancamento = lancamentoRepo.findById(lancamento)
                .orElseThrow(() -> new ObjectNotFoundException("Lancamento não encontrada: id=" + lancamento));

        PagamentoMapper.copyToEntity(pagamentoDTO, pagamento, contaOrigem, lancamento);

        return PagamentoMapper.toDto(contaOrigemRepo.save(pagamento));
    }

}
