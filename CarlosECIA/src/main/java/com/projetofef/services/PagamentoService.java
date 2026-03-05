package com.projetofef.services;

import com.projetofef.domains.Lancamento;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.PagamentoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.projetofef.domains.dtos.PagamentoDTO;
import com.projetofef.mappers.PagamentoMapper;
import com.projetofef.domains.Pagamento;
import java.util.List;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.domains.ContaBancaria;

@Service
public class PagamentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final PagamentoRepository pagamentoRepo;
    private final ContaBancariaRepository contaBancariaRepo;
    private final LancamentoRepository lancamentoRepo;

    public PagamentoService(PagamentoRepository pagamentoRepo, ContaBancariaRepository contaBancariaRepo, LancamentoRepository lancamentoRepo) {
        this.pagamentoRepo = pagamentoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
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
    public Page<PagamentoDTO> findAllByContaBancaria(Integer contaBancaria, Pageable pageable) {
        if (contaBancaria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaBancaria é obrigatório");
        }

        if (!contaBancariaRepo.existsById(contaBancaria)) {
            throw new ObjectNotFoundException("Conta Bancaria " + contaBancaria + "não encontrado");
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

        Page<Pagamento> page = pagamentoRepo.findByContaBancaria_Id(contaBancaria, effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllByContaBancaria(Integer contaBancaria) {
        return findAllByContaBancaria(contaBancaria, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAllByLancamento(Integer lancamentoId, Pageable pageable) {
        if (lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lancamentoId é obrigatório");
        }

        if (!contaBancariaRepo.existsById(lancamentoId)) {
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
    public Page<PagamentoDTO> findAllByContaBancariaAndLancamento(Integer contaBancaria, Integer lancamento, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancaria)) {
            throw new ObjectNotFoundException("Conta Bancaria " + contaBancaria + " não encontrado");
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

        Page<Pagamento> page = pagamentoRepo.findByContaBancaria_IdAndLancamento_Id(contaBancaria, lancamento, effective);
        return PagamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllByContaBancariaAndLancamento(Integer contaBancaria, Integer lancamento) {
        return findAllByContaBancariaAndLancamento(contaBancaria, lancamento, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Pagamento é obrigatório");
        }

        return pagamentoRepo.findById(id)
                .map(PagamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));
    }


    @Transactional
    public PagamentoDTO create(PagamentoDTO pagamentoDTO) {

        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Integer contaBancariaId = pagamentoDTO.getContaBancariaId();
        Integer lancamentoId = pagamentoDTO.getLancamentoId();

        if (contaBancariaId == null || lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A conta bancaria e o Lancamento são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("Conta Bancaria não encontrado: id=" + contaBancariaId));

        Lancamento lancamento = lancamentoRepo.findById(lancamentoId)
                .orElseThrow(() -> new ObjectNotFoundException("Lancamento não encontrada: id=" + lancamentoId));

        pagamentoDTO.setId(null);
        Pagamento pagamento;
        try{
            pagamento = PagamentoMapper.toEntity(pagamentoDTO, contaBancaria, lancamento);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return PagamentoMapper.toDto(pagamentoRepo.save(pagamento));
    }

    @Transactional
    public PagamentoDTO update(Integer id, PagamentoDTO pagamentoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (pagamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Pagamento pagamento = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        Integer contaBancaria = pagamentoDTO.getContaBancariaId();
        Integer lancamento = pagamentoDTO.getLancamentoId();

        if (contaBancaria == null || lancamento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A conta bancaria e o Lancamento são obrigatórios");
        }

        ContaBancaria contaBancaria1 = contaBancariaRepo.findById(contaBancaria)
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancaria não encontrado: id=" + contaBancaria));

        Lancamento lancamento1 = lancamentoRepo.findById(lancamento)
                .orElseThrow(() -> new ObjectNotFoundException("Lancamento não encontrada: id=" + lancamento));

        PagamentoMapper.copyToEntity(pagamentoDTO, pagamento, contaBancaria1, lancamento1);

        return PagamentoMapper.toDto(pagamentoRepo.save(pagamento));
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Pagamento pagamento = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));


        pagamentoRepo.delete(pagamento);
    }

}
