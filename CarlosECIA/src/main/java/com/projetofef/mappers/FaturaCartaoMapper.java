package com.projetofef.mappers;

import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.FaturaCartao;
import com.projetofef.domains.dtos.FaturaCartaoDTO;
import com.projetofef.domains.enums.StatusFatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class FaturaCartaoMapper {
    private  FaturaCartaoMapper() {}

    public static FaturaCartaoDTO toDto(FaturaCartao fa) {
        if(fa == null) return null;

        Integer idDto = fa.getId();
        Integer cartaoCreditoId = (fa.getCartaoCredito() == null) ? null : fa.getCartaoCredito().getId();
        int statusFaturaInt = fa.getStatusFatura().getId();

        return new FaturaCartaoDTO(
                idDto,
                fa.getValorTotal(),
                cartaoCreditoId,
                statusFaturaInt
        );
    }

    public static List<FaturaCartaoDTO> toDtoList(Collection<FaturaCartao> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(FaturaCartaoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<FaturaCartaoDTO> toDtoPage(Page<FaturaCartao> page) {
        List<FaturaCartaoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static FaturaCartao toEntity(FaturaCartaoDTO dto, CartaoCredito cartaoCredito) {
        if(dto == null) return null;

        FaturaCartao fa = new FaturaCartao();

        fa.setId(dto.getId());
        fa.setValorTotal(dto.getValorTotal());
        fa.setCartaoCredito(cartaoCredito);
        fa.setStatusFatura(StatusFatura.toEnum(dto.getStatusFatura()));

        return fa;
    }

    public static FaturaCartao toEntity(FaturaCartaoDTO dto, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null) return null;
        CartaoCredito cartaoCredito = (dto.getCartaoCreditoId() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCreditoId());
        return toEntity(dto, cartaoCredito);
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, CartaoCredito cartaoCredito) {
        if(dto == null || target == null) return;

        target.setValorTotal(dto.getValorTotal());
        target.setCartaoCredito(cartaoCredito);
        target.setStatusFatura(StatusFatura.toEnum(dto.getStatusFatura()));
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null || target == null) return;
        CartaoCredito cartaoCredito = (dto.getCartaoCreditoId() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCreditoId());
        copyToEntity(dto, target, cartaoCredito);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}