package com.projetofef.mappers;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Investimento;
import com.projetofef.domains.MovimentoConta;
import com.projetofef.domains.dtos.MovimentoContaDTO;
import com.projetofef.domains.enums.TipoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MovimentoContaMapper {

    private MovimentoContaMapper() {
    }

    public static MovimentoContaDTO toDto(MovimentoConta mo) {
        if (mo == null) return null;

        Integer idDto = mo.getId();
        Integer contaBancariaId = (mo.getContaBancaria() == null) ? null : mo.getContaBancaria().getId();
        Integer investimentoId = (mo.getInvestimento() == null) ? null : mo.getContaBancaria().getId();
        int tipoTransacaoInt = mo.getTipoTransacao().getId();

        return new MovimentoContaDTO(
                idDto,
                mo.getTipo(),
                mo.getValor(),
                mo.getHistorico(),
                contaBancariaId,
                investimentoId,
                tipoTransacaoInt
        );
    }
    public static List<MovimentoContaDTO> toDtoList(Collection<MovimentoConta> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(MovimentoContaMapper::toDto)
                .collect(Collectors.toList());
    }
    public static Page<MovimentoContaDTO> toDtoPage(Page<MovimentoConta> page) {
        List<MovimentoContaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
    public static MovimentoConta toEntity(MovimentoContaDTO dto, ContaBancaria contaBancaria, Investimento investimento) {
        if(dto == null) return null;

        MovimentoConta mo = new MovimentoConta();

        mo.setId(dto.getId());
        mo.setTipo(trim(dto.getTipo()));
        mo.setValor(dto.getValor());
        mo.setHistorico(trim(dto.getHistorico()));
        mo.setContaBancaria(contaBancaria);
        mo.setInvestimento(investimento);
        mo.setTipoTransacao(TipoTransacao.toEnum(dto.getTipoTransacao()));

        return mo;
    }
    public static MovimentoConta toEntity(MovimentoContaDTO dto, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, Investimento> investimentoResolver) {
        if(dto == null) return null;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Investimento investimento = (dto.getInvestimentoId() == null) ? null : investimentoResolver.apply(dto.getInvestimentoId());
        return toEntity(dto, contaBancaria, investimento);
    }
    public static void copyToEntity(MovimentoContaDTO dto, MovimentoConta target, ContaBancaria contaBancaria, Investimento investimento) {
        if(dto == null || target == null) return;

        target.setTipo(trim(dto.getTipo()));
        target.setValor(dto.getValor());
        target.setHistorico(trim(dto.getHistorico()));
        target.setContaBancaria(contaBancaria);
        target.setInvestimento(investimento);
        target.setTipoTransacao(TipoTransacao.toEnum(dto.getTipoTransacao()));
    }
    public static void copyToEntity(MovimentoContaDTO dto, MovimentoConta target, Function <Integer, ContaBancaria> contaBancariaResolver, Function <Integer, Investimento> investimentoResolver) {
        if(dto == null || target == null) return;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Investimento investimento = (dto.getInvestimentoId() == null) ? null : investimentoResolver.apply(dto.getInvestimentoId());
        copyToEntity(dto, target, contaBancaria, investimento);
    }
    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}