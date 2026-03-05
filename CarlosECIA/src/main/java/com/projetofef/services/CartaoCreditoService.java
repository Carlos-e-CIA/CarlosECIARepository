package com.projetofef.services;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.dtos.CartaoCreditoDTO;
import com.projetofef.mappers.CartaoCreditoMapper;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.FaturaCartaoRepository;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.resources.exceptions.ResourceExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import java.util.List;

@Service
public class CartaoCreditoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final CartaoCreditoRepository cartaoCreditoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LancamentoRepository lancamentoRepo;
    private final FaturaCartaoRepository faturaCartaoRepo;

    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepo, UsuarioRepository usuarioRepo, LancamentoRepository lancamentoRepo, FaturaCartaoRepository faturaCartaoRepo) {
        this.cartaoCreditoRepo = cartaoCreditoRepo;
        this.usuarioRepo = usuarioRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.faturaCartaoRepo = faturaCartaoRepo;
    }

    @Transactional(readOnly = true)
    public List<CartaoCreditoDTO> findAll() {
        return CartaoCreditoMapper.toDtoList(cartaoCreditoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<CartaoCreditoDTO> findAll(Pageable pageable) {
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

        Page<CartaoCredito> page = cartaoCreditoRepo.findAll(effective);
        return CartaoCreditoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<CartaoCreditoDTO> findAllByUsuario(Integer usuarioId, Pageable pageable) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }

        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + "não encontrado");
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

        Page<CartaoCredito> page = cartaoCreditoRepo.findByUsuario_Id(usuarioId, effective);
        return CartaoCreditoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<CartaoCreditoDTO> findAllByUsuario(Integer usuarioId) {
        return findAllByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public CartaoCreditoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de CartaoCredito é obrigatório");
        }

        return cartaoCreditoRepo.findById(id)
                .map(CartaoCreditoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id = " + id));
    }

    @Transactional(readOnly = true)
    public CartaoCreditoDTO findByApelido(String apelido) {
        if (apelido == null || apelido.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apelido do CartaoCredito é obrigatório");
        }

        String normalizedApelido = apelido.trim();

        return cartaoCreditoRepo.findByApelido(normalizedApelido)
                .map(CartaoCreditoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: Apelido=" + normalizedApelido));
    }

    @Transactional
    public CartaoCreditoDTO create(CartaoCreditoDTO cartaoCreditoDTO) {

        if (cartaoCreditoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do grupo são obrigatórios");
        }

        Integer usuarioId = cartaoCreditoDTO.getUsuarioId();

        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));

        cartaoCreditoDTO.setId(null);
        CartaoCredito cartaoCredito;
        try{
            cartaoCredito = CartaoCreditoMapper.toEntity(cartaoCreditoDTO, usuario);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return CartaoCreditoMapper.toDto(cartaoCreditoRepo.save(cartaoCredito));
    }

    @Transactional
    public CartaoCreditoDTO update(Integer id, CartaoCreditoDTO cartaoCreditoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (cartaoCreditoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cartaoCredito são obrigatórios");
        }

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id = " + id));

        Integer usuarioId = cartaoCreditoDTO.getUsuarioId();

        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));

        CartaoCreditoMapper.copyToEntity(cartaoCreditoDTO, cartaoCredito, usuario);

        return CartaoCreditoMapper.toDto(cartaoCreditoRepo.save(cartaoCredito));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id = " + id));

        if (lancamentoRepo.existsByCartaoCredito_Id(id)) {
            throw new DataIntegrityViolationException(
                    "CartaoCredito possui Lancamentos associados e não pode ser removido: id = " + id
            );
        }

        if (faturaCartaoRepo.existsByCartaoCredito_Id(id)) {
            throw new DataIntegrityViolationException(
                    "CartaoCredito possui FaturaCartaos associadas e não pode ser removido: id = " + id
            );
        }

        cartaoCreditoRepo.delete(cartaoCredito);
    }
}