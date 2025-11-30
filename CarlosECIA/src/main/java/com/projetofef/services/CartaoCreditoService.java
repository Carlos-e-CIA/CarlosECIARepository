package com.projetofef.carlosecia.services;

import com.projetofef.carlosecia.domains.CartaoCredito;
import com.projetofef.carlosecia.domains.dtos.CartaoCreditoDTO;
import com.projetofef.carlosecia.mappers.CartaoCreditoMapper;
import com.projetofef.carlosecia.repositories.CartaoCreditoRepository;
import com.projetofef.carlosecia.repositories.FaturaCartaoRepository;
import com.projetofef.carlosecia.repositories.LancamentoRepository;
import com.projetofef.carlosecia.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepo;
    private final FaturaCartaoRepository faturaCartaoRepo;
    private final LancamentoRepository lancamentoRepo;

    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepo, FaturaCartaoRepository faturaCartaoRepo, LancamentoRepository lancamentoRepo) {
        this.cartaoCreditoRepo = cartaoCreditoRepo;
        this.faturaCartaoRepo = faturaCartaoRepo;
        this.lancamentoRepo = lancamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<CartaoCreditoDTO> findAll() {
        return CartaoCreditoMapper.toDtoList(cartaoCreditoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public CartaoCreditoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return cartaoCreditoRepo.findById(id)
                .map(CartaoCreditoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id=" + id));
    }

    @Transactional
    public CartaoCreditoDTO create(CartaoCreditoDTO cartaoCreditoDTO) {

        if (cartaoCreditoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da cartaoCredito são obrigatórios");
        }

        cartaoCreditoDTO.setId(null);
        CartaoCredito cartaoCredito;
        try{
            cartaoCredito = CartaoCreditoMapper.toEntity(cartaoCreditoDTO);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da cartaoCredito são obrigatórios");
        }

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id=" + id));

        CartaoCreditoMapper.copyToEntity(cartaoCreditoDTO, cartaoCredito);

        return CartaoCreditoMapper.toDto(cartaoCreditoRepo.save(cartaoCredito));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        CartaoCredito cartaoCredito = cartaoCreditoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CartaoCredito não encontrado: id=" + id));

        if (faturaCartaoRepo.existsByCartaoCredito_Id(id)) {
            throw new DataIntegrityViolationException(
                    "CartaoCredito possui FaturaCartaos associados e não pode ser removido: id=" + id
            );
        }

        if (lancamentoRepo.existsByCartaoCredito_Id(id)) {
            throw new DataIntegrityViolationException(
                    "CartaoCredito possui Lancamentos associados e não pode ser removido: id=" + id
            );
        }

        cartaoCreditoRepo.delete(cartaoCredito);
    }
}
