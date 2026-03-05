package com.projetofef.services;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.domains.Usuario;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.domains.Entidade;
import com.projetofef.repositories.EntidadeRepository;
import com.projetofef.domains.CentroCusto;
import com.projetofef.repositories.CentroCustoRepository;
import com.projetofef.domains.CartaoCredito;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.domains.Lancamento;
import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.mappers.LancamentoMapper;
import com.projetofef.repositories.RecebimentoRepository;
import com.projetofef.repositories.PagamentoRepository;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LancamentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final LancamentoRepository lancamentoRepo;
    private final ContaBancariaRepository contaBancariaRepo;
    private final UsuarioRepository usuarioRepo;
    private final EntidadeRepository entidadeRepo;
    private final CentroCustoRepository centroCustoRepo;
    private final CartaoCreditoRepository cartaoCreditoRepo;
    private final RecebimentoRepository recebimentoRepo;
    private final PagamentoRepository pagamentoRepo;

    public LancamentoService(LancamentoRepository lancamentoRepo, ContaBancariaRepository contaBancariaRepo, UsuarioRepository usuarioRepo, EntidadeRepository entidadeRepo, CentroCustoRepository centroCustoRepo, CartaoCreditoRepository cartaoCreditoRepo, RecebimentoRepository recebimentoRepo, PagamentoRepository pagamentoRepo) {
        this.lancamentoRepo = lancamentoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
        this.usuarioRepo = usuarioRepo;
        this.entidadeRepo = entidadeRepo;
        this.centroCustoRepo = centroCustoRepo;
        this.cartaoCreditoRepo = cartaoCreditoRepo;
        this.recebimentoRepo = recebimentoRepo;
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
        } else {
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
    public Page<LancamentoDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaBancariaId é obrigatório");
        }
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_Id(contaBancariaId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancaria(Integer contaBancariaId) {
        return findAllByContaBancaria(contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuario(Integer usuarioId, Pageable pageable) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_Id(usuarioId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuario(Integer usuarioId) {
        return findAllByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByEntidade(Integer entidadeId, Pageable pageable) {
        if (entidadeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "entidadeId é obrigatório");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByEntidade_Id(entidadeId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByEntidade(Integer entidadeId) {
        return findAllByEntidade(entidadeId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByCentroCusto(Integer centroCustoId, Pageable pageable) {
        if (centroCustoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "centroCustoId é obrigatório");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByCentroCusto_Id(centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByCentroCusto(Integer centroCustoId) {
        return findAllByCentroCusto(centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByCartaoCredito(Integer cartaoCreditoId, Pageable pageable) {
        if (cartaoCreditoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cartaoCreditoId é obrigatório");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByCartaoCredito_Id(cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByCartaoCredito(Integer cartaoCreditoId) {
        return findAllByCartaoCredito(cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuario(Integer contaBancariaId, Integer usuarioId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_Id(contaBancariaId, usuarioId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuario(Integer contaBancariaId, Integer usuarioId) {
        return findAllByContaBancariaAndUsuario(contaBancariaId, usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndEntidade(Integer contaBancariaId, Integer entidadeId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndEntidade_Id(contaBancariaId, entidadeId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndEntidade(Integer contaBancariaId, Integer entidadeId) {
        return findAllByContaBancariaAndEntidade(contaBancariaId, entidadeId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndCentroCusto(Integer contaBancariaId, Integer centroCustoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndCentroCusto_Id(contaBancariaId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndCentroCusto(Integer contaBancariaId, Integer centroCustoId) {
        return findAllByContaBancariaAndCentroCusto(contaBancariaId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndCartaoCredito(Integer contaBancariaId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndCartaoCredito_Id(contaBancariaId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndCartaoCredito(Integer contaBancariaId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndCartaoCredito(contaBancariaId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndEntidade(Integer usuarioId, Integer entidadeId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndEntidade_Id(usuarioId, entidadeId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndEntidade(Integer usuarioId, Integer entidadeId) {
        return findAllByUsuarioAndEntidade(usuarioId, entidadeId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndCentroCusto(Integer usuarioId, Integer centroCustoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndCentroCusto_Id(usuarioId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndCentroCusto(Integer usuarioId, Integer centroCustoId) {
        return findAllByUsuarioAndCentroCusto(usuarioId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndCartaoCredito(Integer usuarioId, Integer cartaoCreditoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndCartaoCredito_Id(usuarioId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndCartaoCredito(Integer usuarioId, Integer cartaoCreditoId) {
        return findAllByUsuarioAndCartaoCredito(usuarioId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByEntidadeAndCentroCusto(Integer entidadeId, Integer centroCustoId, Pageable pageable) {
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByEntidade_IdAndCentroCusto_Id(entidadeId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByEntidadeAndCentroCusto(Integer entidadeId, Integer centroCustoId) {
        return findAllByEntidadeAndCentroCusto(entidadeId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByEntidadeAndCartaoCredito(Integer entidadeId, Integer cartaoCreditoId, Pageable pageable) {
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByEntidade_IdAndCartaoCredito_Id(entidadeId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByEntidadeAndCartaoCredito(Integer entidadeId, Integer cartaoCreditoId) {
        return findAllByEntidadeAndCartaoCredito(entidadeId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByCentroCustoAndCartaoCredito(Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByCentroCusto_IdAndCartaoCredito_Id(centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByCentroCustoAndCartaoCredito(Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByCentroCustoAndCartaoCredito(centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidade(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndEntidade_Id(contaBancariaId, usuarioId, entidadeId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidade(Integer contaBancariaId, Integer usuarioId, Integer entidadeId) {
        return findAllByContaBancariaAndUsuarioAndEntidade(contaBancariaId, usuarioId, entidadeId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCentroCusto(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndCentroCusto_Id(contaBancariaId, usuarioId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCentroCusto(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId) {
        return findAllByContaBancariaAndUsuarioAndCentroCusto(contaBancariaId, usuarioId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndCartaoCredito_Id(contaBancariaId, usuarioId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndUsuarioAndCartaoCredito(contaBancariaId, usuarioId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCentroCusto(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndEntidade_IdAndCentroCusto_Id(contaBancariaId, entidadeId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCentroCusto(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId) {
        return findAllByContaBancariaAndEntidadeAndCentroCusto(contaBancariaId, entidadeId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCartaoCredito(Integer contaBancariaId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndEntidade_IdAndCartaoCredito_Id(contaBancariaId, entidadeId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCartaoCredito(Integer contaBancariaId, Integer entidadeId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndEntidadeAndCartaoCredito(contaBancariaId, entidadeId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndCentroCusto_IdAndCartaoCredito_Id(contaBancariaId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndCentroCustoAndCartaoCredito(contaBancariaId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCusto(Integer usuarioId, Integer entidadeId, Integer centroCustoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndEntidade_IdAndCentroCusto_Id(usuarioId, entidadeId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCusto(Integer usuarioId, Integer entidadeId, Integer centroCustoId) {
        return findAllByUsuarioAndEntidadeAndCentroCusto(usuarioId, entidadeId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndEntidadeAndCartaoCredito(Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndEntidade_IdAndCartaoCredito_Id(usuarioId, entidadeId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndEntidadeAndCartaoCredito(Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId) {
        return findAllByUsuarioAndEntidadeAndCartaoCredito(usuarioId, entidadeId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndCentroCustoAndCartaoCredito(Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndCentroCusto_IdAndCartaoCredito_Id(usuarioId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndCentroCustoAndCartaoCredito(Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByUsuarioAndCentroCustoAndCartaoCredito(usuarioId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByEntidadeAndCentroCustoAndCartaoCredito(Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(entidadeId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByEntidadeAndCentroCustoAndCartaoCredito(Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByEntidadeAndCentroCustoAndCartaoCredito(entidadeId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCusto(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCentroCusto_Id(contaBancariaId, usuarioId, entidadeId, centroCustoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCusto(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId) {
        return findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCusto(contaBancariaId, usuarioId, entidadeId, centroCustoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCartaoCredito_Id(contaBancariaId, usuarioId, entidadeId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndUsuarioAndEntidadeAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndCentroCusto_IdAndCartaoCredito_Id(contaBancariaId, usuarioId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndUsuarioAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(contaBancariaId, entidadeId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndEntidadeAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, entidadeId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByUsuario_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(usuarioId, entidadeId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(usuarioId, entidadeId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable) {
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrado");
        }
        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + " não encontrado");
        }
        if (!entidadeRepo.existsById(entidadeId)) {
            throw new ObjectNotFoundException("Entidade id " + entidadeId + " não encontrado");
        }
        if (!centroCustoRepo.existsById(centroCustoId)) {
            throw new ObjectNotFoundException("CentroCusto id " + centroCustoId + " não encontrado");
        }
        if (!cartaoCreditoRepo.existsById(cartaoCreditoId)) {
            throw new ObjectNotFoundException("CartaoCredito id " + cartaoCreditoId + " não encontrado");
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

        Page<Lancamento> page = lancamentoRepo.findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(contaBancariaId, usuarioId, entidadeId, centroCustoId, cartaoCreditoId, effective);
        return LancamentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId) {
        return findAllByContaBancariaAndUsuarioAndEntidadeAndCentroCustoAndCartaoCredito(contaBancariaId, usuarioId, entidadeId, centroCustoId, cartaoCreditoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public LancamentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Lancamento é obrigatório");
        }

        return lancamentoRepo.findById(id)
                .map(LancamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: id=" + id));
    }

    @Transactional(readOnly = true)
    public LancamentoDTO findByDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descricao do Lancamento é obrigatório");
        }

        String normalizedDescricao = descricao.trim();

        return lancamentoRepo.findByDescricao(normalizedDescricao)
                .map(LancamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: Descricao=" + normalizedDescricao));
    }

    @Transactional
    public LancamentoDTO create(LancamentoDTO lancamentoDTO) {

        if (lancamentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do grupo são obrigatórios");
        }

        Integer contaBancariaId = lancamentoDTO.getContaBancariaId();
        Integer usuarioId = lancamentoDTO.getUsuarioId();
        Integer entidadeId = lancamentoDTO.getEntidadeId();
        Integer centroCustoId = lancamentoDTO.getCentroCustoId();
        Integer cartaoCreditoId = lancamentoDTO.getCartaoCreditoId();

        if (contaBancariaId == null || usuarioId == null || entidadeId == null || centroCustoId == null || cartaoCreditoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ContaBancaria, Usuario, Entidade, CentroCusto e CartaoCredito são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrado: id=" + contaBancariaId));

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id=" + usuarioId));

        Entidade entidade = entidadeRepo.findById(entidadeId)
                .orElseThrow(() -> new ObjectNotFoundException("Entidade não encontrado: id=" + entidadeId));

        CentroCusto centroCusto = centroCustoRepo.findById(centroCustoId)
                .orElseThrow(() -> new ObjectNotFoundException("CentroCusto não encontrado: id=" + centroCustoId));

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(cartaoCreditoId)
                .orElseThrow(() -> new ObjectNotFoundException("CartaoCredito não encontrado: id=" + cartaoCreditoId));

        lancamentoDTO.setId(null);
        Lancamento lancamento;
        try {
            lancamento = LancamentoMapper.toEntity(lancamentoDTO, contaBancaria, usuario, entidade, centroCusto, cartaoCredito);
        } catch (IllegalArgumentException ex) {
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do lancamento são obrigatórios");
        }

        Lancamento lancamento = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: id=" + id));

        Integer contaBancariaId = lancamentoDTO.getContaBancariaId();
        Integer usuarioId = lancamentoDTO.getUsuarioId();
        Integer entidadeId = lancamentoDTO.getEntidadeId();
        Integer centroCustoId = lancamentoDTO.getCentroCustoId();
        Integer cartaoCreditoId = lancamentoDTO.getCartaoCreditoId();

        if (contaBancariaId == null || usuarioId == null || entidadeId == null || centroCustoId == null || cartaoCreditoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ContaBancaria, Usuario, Entidade, CentroCusto e CartaoCredito são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrado: id=" + contaBancariaId));

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id=" + usuarioId));

        Entidade entidade = entidadeRepo.findById(entidadeId)
                .orElseThrow(() -> new ObjectNotFoundException("Entidade não encontrado: id=" + entidadeId));

        CentroCusto centroCusto = centroCustoRepo.findById(centroCustoId)
                .orElseThrow(() -> new ObjectNotFoundException("CentroCusto não encontrado: id=" + centroCustoId));

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(cartaoCreditoId)
                .orElseThrow(() -> new ObjectNotFoundException("CartaoCredito não encontrado: id=" + cartaoCreditoId));

        LancamentoMapper.copyToEntity(lancamentoDTO, lancamento, contaBancaria, usuario, entidade, centroCusto, cartaoCredito);

        return LancamentoMapper.toDto(lancamentoRepo.save(lancamento));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Lancamento lancamento = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lancamento não encontrado: id=" + id));

        if (recebimentoRepo.existsByLancamento_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Lancamento possui Recimentos associados e não pode ser removido: id=" + id
            );
        }

        if (pagamentoRepo.existsByLancamento_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Lancamento possui Pagamentos associados e não pode ser removido: id=" + id
            );
        }

        lancamentoRepo.delete(lancamento);
    }
}