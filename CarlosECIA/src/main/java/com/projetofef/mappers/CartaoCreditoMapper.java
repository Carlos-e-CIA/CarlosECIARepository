package com.projetofef.mappers;

import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.dtos.CartaoCreditoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CartaoCreditoMapper {
    private CartaoCreditoMapper() {}

    public static CartaoCreditoDTO toDto(CartaoCredito ca) {
        if(ca == null) return null;

        return new CartaoCreditoDTO(
                ca.getId(),
                ca.getBandeira(),
                ca.getEmissor(),
                ca.getApelido(),
                ca.ativo()
        );
    }

    public static CartaoCredito toEntity(CartaoCreditoDTO dto) {
        if(dto == null) return null;
        CartaoCredito ca = new CartaoCredito();
        ca.setId(dto.getId());
        ca.setBandeira(dto.getBandeira() == null ? null : dto.getBandeira().trim());
        ca.setEmissor(dto.getEmissor() == null ? null : dto.getEmissor().trim());
        ca.setApelido(dto.getApelido() == null ? null : dto.getApelido().trim());
        ca.setAtivo(dto.getAtivo() == null ? 'N' : dto.getAtivo());
        return ca;
    }

    public static void copyToEntity(CartaoCreditoDTO dto, CartaoCredito target) {
        if(dto == null || target == null) return;
        target.setBandeira(dto.getBandeira() == null ? null : dto.getBandeira().trim());
        target.setEmissor(dto.getEmissor() == null ? null : dto.getEmissor().trim());
        target.setApelido(dto.getApelido() == null ? null : dto.getApelido().trim());
        target.setAtivo(dto.getAtivo() == null ? null : Character.toUpperCase(dto.getAtivo()));
    }

    public static List<CartaoCreditoDTO> toDtoList(Collection<CartaoCredito> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(CartaoCreditoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<CartaoCredito> toEntity(Collection<CartaoCreditoDTO> dtos) {
        if(dtos == null) return List.of();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(CartaoCreditoMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static Page<CartaoCreditoDTO> toDTOPage(Page<CartaoCredito> page) {
        List<CartaoCreditoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
