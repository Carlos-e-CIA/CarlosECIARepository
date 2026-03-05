package com.projetofef.mappers;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Investimento;
import com.projetofef.domains.dtos.InvestimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class InvestimentoMapper {
    private  InvestimentoMapper() {}

    public static InvestimentoDTO toDto(Investimento in) {
        if(in == null) return null;

        Integer idDto = in.getId();
        Integer contaBancariaId = (in.getContaBancaria() == null) ? null : in.getContaBancaria().getId();

        return new InvestimentoDTO(
                idDto,
                in.getTipo(),
                in.getCodigo(),
                in.getValor(),
                in.getJuros(),
                contaBancariaId
        );
    }

    public static List<InvestimentoDTO> toDtoList(Collection<Investimento> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(InvestimentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<InvestimentoDTO> toDtoPage(Page<Investimento> page) {
        List<InvestimentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Investimento toEntity(InvestimentoDTO dto, ContaBancaria contaBancaria) {
        if(dto == null) return null;

        Investimento in = new Investimento();

        in.setId(dto.getId());
        in.setTipo(trim(dto.getTipo()));
        in.setCodigo(trim(dto.getCodigo()));
        in.setValor(dto.getValor());
        in.setJuros(dto.getJuros());
        in.setContaBancaria(contaBancaria);

        return in;
    }

    public static Investimento toEntity(InvestimentoDTO dto, Function<Integer, ContaBancaria> contaBancariaResolver) {
        if(dto == null) return null;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        return toEntity(dto, contaBancaria);
    }

    public static void copyToEntity(InvestimentoDTO dto, Investimento target, ContaBancaria contaBancaria) {
        if(dto == null || target == null) return;

        target.setTipo(trim(dto.getTipo()));
        target.setCodigo(trim(dto.getCodigo()));
        target.setValor(dto.getValor());
        target.setJuros(dto.getJuros());
        target.setContaBancaria(contaBancaria);
    }

    public static void copyToEntity(InvestimentoDTO dto, Investimento target, Function <Integer, ContaBancaria> contaBancariaResolver) {
        if(dto == null || target == null) return;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        copyToEntity(dto, target, contaBancaria);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}