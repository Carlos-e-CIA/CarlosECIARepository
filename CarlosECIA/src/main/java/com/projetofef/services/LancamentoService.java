package com.projetofef.services;

import com.projetofef.domains.*;
import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.domains.dtos.PagamentoDTO;
import com.projetofef.mappers.LancamentoMapper;
import com.projetofef.mappers.PagamentoMapper;
import com.projetofef.repositories.*;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class LancamentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final LancamentoRepository lancamentoRepo;
    private final UsuarioRepository usuarioRepo;
    private final EntidadeRepository entidadeRepo;
    private final CentroCustoRepository centroCustoRepo;
    private final ContaBancariaRepository contaBancariaRepo;
    private final CartaoCreditoRepository cartaoCreditoRepo;
    private final PagamentoRepository pagamentoRepo;



    public LancamentoService(LancamentoRepository lancamentoRepo, UsuarioRepository usuarioRepo, EntidadeRepository entidadeRepo, CentroCustoRepository centroCustoRepo, ContaBancariaRepository contaBancariaRepo, CartaoCreditoRepository cartaoCreditoRepo, PagamentoRepository pagamentoRepo) {
        this.lancamentoRepo = lancamentoRepo;
        this.usuarioRepo = usuarioRepo;
        this.entidadeRepo = entidadeRepo;
        this.centroCustoRepo = centroCustoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
        this.cartaoCreditoRepo = cartaoCreditoRepo;
        this.pagamentoRepo = pagamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAll() {
        return LancamentoMapper.toDtoList(lancamentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAll(Pageable pageable) {
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

        Page<Lancamento> page = lancamentoRepo.findAll(effective);
        return LancamentoMapper.toDtoPage(page);
    }


    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuario(Integer usuario, Pageable pageable) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario é obrigatório");
        }

        if (!usuarioRepo.existsById(usuario)) {
            throw new ObjectNotFoundException("Usuário " + usuario + "não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_Id(usuario, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuario(Integer usuario) {
        return findAllByUsuario(usuario, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByEntidade(Integer entidade, Pageable pageable) {
        if (entidade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "entidade é obrigatório");
        }

        if (!entidade.existsById(entidade)) {
            throw new ObjectNotFoundException("Entidade id " + lancamentoId + "não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByEntidade_Id(entidade, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByEntidade(Integer entidade) {
        return findAllByEntidade(entidade, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByCentroCusto(Integer centroCusto, Pageable pageable) {
        if (centroCusto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Centro custo é obrigatório");
        }

        if (!centroCusto.existsById(centroCusto)) {
            throw new ObjectNotFoundException("Centro custo id " + centroCusto + "não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByCentroCusto_Id(centroCusto, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByCentroCusto(Integer centroCusto) {
        return findAllByCentroCusto(centroCusto, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancaria(Integer contaBancaria, Pageable pageable) {
        if (contaBancaria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta bancaria é obrigatório");
        }

        if (!contaBancaria.existsById(contaBancaria)) {
            throw new ObjectNotFoundException("Conta bancaria id " + contaBancaria + "não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_Id(contaBancaria, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancaria(Integer contaBancaria) {
        return findAllByContaBancaria(contaBancaria, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByCartaoCredito(Integer cartaoCredito, Pageable pageable) {
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

        Page<Lancamento> page = lancamentoRepo.findByCartaoCredito_Id(cartaoCredito, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByCartaoCredito(Integer cartaoCredito) {
        return findAllByCartaoCredito(cartaoCredito, Pageable.unpaged()).getContent();
    }


    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCustoAndContaBancariaAndCartaoCredito(Integer usuario, Integer entidade, Integer centroCusto, Integer contaBancaria, Integer cartaoCredito, Pageable pageable) {
        if (!usuarioRepo.existsById(usuario)) {
            throw new ObjectNotFoundException("Usuário " + usuario + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidade)) {
            throw new ObjectNotFoundException("Entidade " + entidade + " não encontrada");
        }
        if (!centroCustoRepo.existsById(centroCusto)) {
            throw new ObjectNotFoundException("Entidade " + centroCusto + " não encontrada");
        }
        if (contaBancariaRepo.existsById(contaBancaria)) {
            throw new ObjectNotFoundException("Conta bancaria " + contaBancaria + " não encontrada");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndEntidade_IdAndCentroCusto_IdAndContaCusto_IdAndCartaoCredito_Id(usuario, entidade, centroCusto, contaBancaria, cartaoCredito, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCustoAndContaBancariaAndCartaoCredito(Integer usuario, Integer entidade, Integer centroCusto, Integer contaBancaria, Integer cartaoCredito) {
        return findAllByUsuarioAndEntidadeAndCentroCustoAndContaBancariaAndCartaoCredito(usuario, entidade, centroCusto, contaBancaria, cartaoCredito, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public LancamentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de lancamento é obrigatório");
        }

        return lancamentoRepo.findById(id)
                .map(LancamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + id));
    }


    @Transactional
    public LancamentoDTO create(LancamentoDTO lancamentoDTO) {

        if (lancamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do lancamento são obrigatórios");
        }

        Integer usuario = lancamentoDTO.getUsuario();
        Integer entidade = lancamentoDTO.getEntidade();
        Integer centroCusto = lancamentoDTO.getCentroCusto();
        Integer contaBancaria = lancamentoDTO.getContaBancaria();
        Integer cartaoCredito = lancamentoDTO.getCartaoCredito();

        if (usuario == null || entidade == null ||  centroCusto == null ||  contaBancaria == null || cartaoCredito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuario, entidade, centroCusto, conta bancaria e o cartão de crédito são obrigatórios");
        }

        Usuario usuario1 = usuarioRepo.findById(usuario)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id=" + usuario));

        Entidade entidade1 = entidadeRepo.findById(entidade)
                .orElseThrow(() -> new ObjectNotFoundException("Entidade não encontrada: id=" + entidade));

        CentroCusto centroCusto1 = entidadeRepo.findById(centroCusto)
                .orElseThrow(() -> new ObjectNotFoundException("Centro custo não encontrada: id=" + centroCusto));

        ContaBancaria contaBancaria1 = entidadeRepo.findById(contaBancaria)
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancária não encontrada: id=" + contaBancaria));

        CartaoCredito cartaoCredito1 = entidadeRepo.findById(cartaoCredito)
                .orElseThrow(() -> new ObjectNotFoundException("Cartão de crédito não encontrada: id=" + cartaoCredito));

        lancamentoDTO.setId(null);
        Lancamento lancamento;
        try{
            lancamento = LancamentoMapper.toEntity(lancamentoDTO, usuario1, entidade1, centroCusto1, contaBancaria1, cartaoCredito1);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return LancamentoMapper.toDto(lancamentoRepo.save(lancamento));
    }

    @Transactional
    public LancamentoDTO update(Integer id, LancamentoDTO lancamentoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (lancamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do LANÇAMENTO são obrigatórios");
        }

        Lancamento lancamento1 = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: id=" + id));

        Integer usuario = lancamentoDTO.getUsuario();
        Integer entidade = lancamentoDTO.getEntidade();
        Integer centroCusto = lancamentoDTO.getCentroCusto();
        Integer contaBancaria = lancamentoDTO.getContaBancaria();
        Integer cartaoCredito = lancamentoDTO.getCartaoCredito();

        if (usuario == null || entidade == null ||  centroCusto == null || contaBancaria == null || cartaoCredito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuario, entidade, centroCusto, conta bancaria e o cartão de crédito são obrigatórios");
        }

        Usuario usuario1 = usuarioRepo.findById(usuario)
                .orElseThrow(() -> new ObjectNotFoundException("usuario não encontrado: id=" + usuario));

        Entidade entidade1 = entidadeRepo.findById(entidade)
                .orElseThrow(() -> new ObjectNotFoundException("entidade não encontrada: id=" + entidade));

        CentroCusto centroCusto1 = entidadeRepo.findById(centroCusto)
                .orElseThrow(() -> new ObjectNotFoundException("Centro custo não encontrada: id=" + centroCusto));

        ContaBancaria contaBancaria1 = entidadeRepo.findById(contaBancaria)
                .orElseThrow(() -> new ObjectNotFoundException("Conta bancaria não encontrada: id=" + contaBancaria));

        CartaoCredito cartaoCredito1 = entidadeRepo.findById(cartaoCredito)
                .orElseThrow(() -> new ObjectNotFoundException("Cartao credito não encontrada: id=" + cartaoCredito));

        LancamentoMapper.copyToEntity(lancamentoDTO, lancamento1, usuario1, entidade1, centroCusto1, contaBancaria1, cartaoCredito1);

        return LancamentoMapper.toDto(lancamentoRepo.save(lancamento1));
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Lancamento lancamento = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: id=" + id));

        if (pagamentoRepo.existsByLancamento_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Lançamento possui pagamentos associados e não pode ser removido: id=" + id
            );
        }


        lancamentoRepo.delete(lancamento);
    }
}
