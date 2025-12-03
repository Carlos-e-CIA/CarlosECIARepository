package com.projetofef.mappers;

import com.projetofef.domains.Pagamento;
import com.projetofef.domains.dtos.PagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.projetofef.domains.ContaBancaria;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PagamentoMapper {
    private  PagamentoMapper() {}

    public static PagamentoDTO toDto(Pagamento p) {
        if(p == null) return null;

        Integer idDto = p.getId();
        Integer idContaOrigem  = (p.getContaBancaria() == null) ? null : p.getContaBancaria().getId();
        Integer idLancamento = (p.getLancamento() == null) ? null : p.getLancamento().getId();

        return new PagamentoDTO(
                idDto,
                p.getValorPago(),
                p.getObservacao(),
                idContaOrigem,
                idLancamento
        );
    }

    public static List<PagamentoDTO> toDtoList(Collection<Pagamento> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(PagamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<PagamentoDTO> toDtoPage(Page<Pagamento> page) {
        List<PagamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Pagamento toEntity(PagamentoDTO dto,  ContaBancaria contaOrigem, Lancamento lancamento) {
        if(dto == null) return null;

        Pagamento p = new Pagamento();

        p.setId(dto.getId());
        p.setValorPago(dto.getValorPago());
        p.setObservacao(trim(dto.getObservacao()));
        p.setContaBancaria(contaOrigem);
        p.setLancamento(lancamento);
        return p;
    }

    public static Pagamento toEntity(PagamentoDTO dto, Function<Integer, ContaBancaria> contaOrigemResolver, Function<Integer, Lancamento> lancamentoResolver) {
        if(dto == null) return null;
        ContaBancaria contaOrigem = (dto.getContaOrigem() == null) ? null : contaOrigemResolver.apply(dto.getContaOrigem());
        Lancamento lancamento = (dto.getLancamento() == null) ? null : lancamentoResolver.apply(dto.getLancamento());
        return toEntity(dto, ContaBancaria, Lancamento);
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, ContaBancaria contaOrigem, Lancamento lancamento) {
        if(dto == null || target == null) return;

        target.setValorPago(dto.getValorPago());
        target.setObservacao(trim(dto.getObservacao()));
        target.setContaBancaria(contaOrigem);
        target.setLancamento(lancamento);
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, Function <Integer, ContaBancaria> contaOrigemResolver, Function<Integer, Lancamento> lancamentoResolver) {
        if(dto == null || target == null) return;
        ContaBancaria contaOrigem = (dto.getContaOrigem() == null) ? null : contaOrigemResolver.apply(dto.getContaOrigem());
        Lancamento lancamento = (dto.getLancamento() == null) ? null : lancamentoResolver.apply(dto.getLancamento());
        copyToEntity(dto, target, contaOrigem, lancamento);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

}
