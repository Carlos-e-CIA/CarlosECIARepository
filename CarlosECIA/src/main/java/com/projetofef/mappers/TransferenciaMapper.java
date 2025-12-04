package com.projetofef.mappers;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Transferencia;
import com.projetofef.domains.dtos.TransferenciaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TransferenciaMapper {

    private TransferenciaMapper() {}

    public static TransferenciaDTO toDto(Transferencia tr) {
        if (tr == null) return null;

        Integer idDto = tr.getId();
        Integer contaBancariaId = (tr.getContaBancaria() == null) ? null : tr.getContaBancaria().getId();

        return new TransferenciaDTO(
                idDto,
                tr.getData(),
                tr.getValor(),
                tr.getObservacao(),
                contaBancariaId
        );
    }

    public static List<TransferenciaDTO> toDtoList(Collection<Transferencia> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(TransferenciaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<TransferenciaDTO> toDtoPage(Page<Transferencia> page) {
        List<TransferenciaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Transferencia toEntity(TransferenciaDTO dto, ContaBancaria contaBancaria) {
        if (dto == null) return null;

        Transferencia tr = new Transferencia();

        tr.setId(dto.getId());
        tr.setData(dto.getData());
        tr.setValor(dto.getValor());
        tr.setObservacao(trim(dto.getObservacao()));
        tr.setContaBancaria(contaBancaria);

        return tr;
    }

    public static Transferencia toEntity(TransferenciaDTO dto,
                                         Function<Integer, ContaBancaria> contaBancariaResolver) {
        if (dto == null) return null;

        ContaBancaria contaBancaria =
                (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());

        return toEntity(dto, contaBancaria);
    }

    public static void copyToEntity(TransferenciaDTO dto, Transferencia target, ContaBancaria contaBancaria) {
        if (dto == null || target == null) return;

        target.setData(dto.getData());
        target.setValor(dto.getValor());
        target.setObservacao(trim(dto.getObservacao()));
        target.setContaBancaria(contaBancaria);
    }

    public static void copyToEntity(TransferenciaDTO dto,
                                    Transferencia target,
                                    Function<Integer, ContaBancaria> contaBancariaResolver) {
        if (dto == null || target == null) return;

        ContaBancaria contaBancaria =
                (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());

        copyToEntity(dto, target, contaBancaria);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}