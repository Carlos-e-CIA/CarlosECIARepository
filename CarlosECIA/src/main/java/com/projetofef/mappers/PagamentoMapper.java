package com.projetofef.mappers;

import com.projetofef.domains.Lancamento;
import com.projetofef.domains.Pagamento;
import com.projetofef.domains.dtos.PagamentoDTO;
import com.projetofef.domains.enums.MeioPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.projetofef.domains.ContaBancaria;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PagamentoMapper {
    private  PagamentoMapper() {}

    public static PagamentoDTO toDto(Pagamento pa) {
        if(pa == null) return null;

        Integer idDto = pa.getId();
        Integer idContaBancaria  = (pa.getContaBancaria() == null) ? null : pa.getContaBancaria().getId();
        Integer idLancamento = (pa.getLancamento() == null) ? null : pa.getLancamento().getId();
        int meioPagamentoInt = pa.getMeioPagamento().getId();

        return new PagamentoDTO(
                idDto,
                pa.getValorPago(),
                pa.getObservacao(),
                idContaBancaria,
                idLancamento,
                meioPagamentoInt
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

    public static Pagamento toEntity(PagamentoDTO dto, ContaBancaria ContaBancaria, Lancamento lancamento) {
        if(dto == null) return null;

        Pagamento pa = new Pagamento();

        pa.setId(dto.getId());
        pa.setValorPago(dto.getValorPago());
        pa.setObservacao(trim(dto.getObservacao()));
        pa.setContaBancaria(ContaBancaria);
        pa.setLancamento(lancamento);
        pa.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));

        return pa;
    }

    public static Pagamento toEntity(PagamentoDTO dto, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, Lancamento> lancamentoResolver) {
        if(dto == null) return null;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamentoResolver.apply(dto.getLancamentoId());
        return toEntity(dto, contaBancaria, lancamento);
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, ContaBancaria contaBancaria, Lancamento lancamento) {
        if(dto == null || target == null) return;

        target.setValorPago(dto.getValorPago());
        target.setObservacao(trim(dto.getObservacao()));
        target.setContaBancaria(contaBancaria);
        target.setLancamento(lancamento);
        target.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, Function <Integer, ContaBancaria> contaBancariaResolver, Function<Integer, Lancamento> lancamentoResolver) {
        if(dto == null || target == null) return;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamentoResolver.apply(dto.getLancamentoId());
        copyToEntity(dto, target, contaBancaria, lancamento);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

}