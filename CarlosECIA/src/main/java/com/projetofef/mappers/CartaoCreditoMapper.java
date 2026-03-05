package com.projetofef.mappers;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.dtos.CartaoCreditoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CartaoCreditoMapper {
    private  CartaoCreditoMapper() {}

    public static CartaoCreditoDTO toDto(CartaoCredito ca) {
        if(ca == null) return null;

        Integer idDto = ca.getId();
        Integer usuarioId = (ca.getUsuario() == null) ? null : ca.getUsuario().getId();

        return new CartaoCreditoDTO(
                idDto,
                ca.getBandeira(),
                ca.getEmissor(),
                ca.getApelido(),
                ca.getAtivo(),
                usuarioId
        );
    }

    public static List<CartaoCreditoDTO> toDtoList(Collection<CartaoCredito> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(CartaoCreditoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<CartaoCreditoDTO> toDtoPage(Page<CartaoCredito> page) {
        List<CartaoCreditoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static CartaoCredito toEntity(CartaoCreditoDTO dto, Usuario usuario) {
        if(dto == null) return null;

        CartaoCredito ca = new CartaoCredito();

        ca.setId(dto.getId());
        ca.setBandeira(trim(dto.getBandeira()));
        ca.setEmissor(trim(dto.getEmissor()));
        ca.setApelido(trim(dto.getApelido()));
        ca.setAtivo(dto.getAtivo());
        ca.setUsuario(usuario);

        return ca;
    }

    public static CartaoCredito toEntity(CartaoCreditoDTO dto, Function<Integer, Usuario> usuarioResolver) {
        if(dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(CartaoCreditoDTO dto, CartaoCredito target, Usuario usuario) {
        if(dto == null || target == null) return;

        target.setBandeira(trim(dto.getBandeira()));
        target.setEmissor(trim(dto.getEmissor()));
        target.setApelido(trim(dto.getApelido()));
        target.setAtivo(dto.getAtivo());
        target.setUsuario(usuario);
    }

    public static void copyToEntity(CartaoCreditoDTO dto, CartaoCredito target, Function <Integer, Usuario> usuarioResolver) {
        if(dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}