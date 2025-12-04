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

public class FaturaCartaoMapper {
    private  FaturaCartaoMapper() {}

    public static FaturaCartaoDTO toDto(FaturaCartao fc) {
        if(fc == null) return null;

        Integer idDto = fc.getId();
        Integer cartaoCredito = (fc.getCartaoCredito() == null) ? null : fc.getCartaoCredito().getId();
        int statusFatura = fc.getStatusFatura().getId();

        return new FaturaCartaoDTO(
                idDto,
                fc.getCompetencia(),
                fc.getValorTotal(),
                cartaoCredito,
                statusFatura
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

        FaturaCartao fc = new FaturaCartao();

        fc.setId(dto.getId());
        fc.setCompetencia(trim(dto.getCompetencia()));
        fc.setValorTotal(dto.getValorTotal());
        fc.setCartaoCredito(cartaoCredito);
        fc.setStatusFatura(StatusFatura.toEnum(dto.getStatusFatura()));

        return fc;
    }

    public static FaturaCartao toEntity(FaturaCartaoDTO dto, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null) return null;
        CartaoCredito cartaoCredito = (dto.getCartaoCredito() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCredito());
        return toEntity(dto, cartaoCredito);
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, CartaoCredito cartaoCredito) {
        if(dto == null || target == null) return;

        target.setCompetencia(trim(dto.getCompetencia()));
        target.setValorTotal(dto.getValorTotal());
        target.setCartaoCredito(cartaoCredito);
        target.setStatusFatura(StatusFatura.toEnum(dto.getStatusFatura()));
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, Function <Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null || target == null) return;
        CartaoCredito cartaoCredito = (dto.getCartaoCredito() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCredito());
        copyToEntity(dto, target, cartaoCredito);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
