package com.projetofef.carlosecia.mappers;

import com.projetofef.carlosecia.domains.Recebimento;
import com.projetofef.carlosecia.domains.ContaBancaria;
import com.projetofef.carlosecia.domains.Lancamento;
import com.projetofef.carlosecia.domains.dtos.RecebimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.aspectj.asm.internal.ProgramElement.trim;

public final class RecebimentoMapper {
    private RecebimentoMapper() {}

    public static RecebimentoDTO toDto(Recebimento e) {
        if (e == null) return null;
        Integer lancamentoId = (e.getLancamento() == null) ? null : e.getLancamento().getId();
        Integer contaBancariaId = (e.getContaBancaria() == null) ? null : e.getContaBancaria().getId();

        return new RecebimentoDTO(
                e.getId(),
                lancamentoId,
                e.getDataRecebimento(),
                e.getValorRecebimento(),
                contaBancariaId,
                e.getObservacao()
        );
    }
    public static List<RecebimentoDTO> toDtoList(Collection<Recebimento> list) {
        if (list == null) return List.of();
        return list.stream()
                .filter(Objects::nonNull)
                .map(RecebimentoMapper::toDto)
                .collect(Collectors.toList());
    }
    public static Page<RecebimentoDTO> toDtoPage(Page<Recebimento> page) {
        List<RecebimentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
    public static Recebimento toEntity(RecebimentoDTO dto, Lancamento lancamento, ContaBancaria contaBancaria) {
        if (dto == null) return null;

        Recebimento e = new Recebimento();

        e.setId(dto.getId());
        e.setLancamento(lancamento);
        e.setValorRecebimento(dto.getValorRecebido());
        e.setContaBancaria(contaBancaria);
        e.setObservacao(trim(dto.getObservacao()));

        return e;
    }
    public static Recebimento toEntity(
            RecebimentoDTO dto,
            Function<Integer, Lancamento> lancamentoResolver,
            Function<Integer, ContaBancaria> contaBancariaResolver
    )
    {if (dto == null) return null;

        Lancamento lancamento = (dto.getLancamentoId() == null) ? null
                : lancamentoResolver.apply(dto.getLancamentoId());

        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null
                : contaBancariaResolver.apply(dto.getContaBancariaId());

        return toEntity(dto, lancamento, contaBancaria);
    }
    public static void copyToEntity(
            RecebimentoDTO dto,
            Recebimento target,
            Lancamento lancamento,
            ContaBancaria contaBancaria
    )
    {
        if (dto == null || target == null) return;

        target.setLancamento(lancamento);
        target.setValorRecebimento(dto.getValorRecebido());
        target.setContaBancaria(contaBancaria);
        target.setObservacao(trim(dto.getObservacao()));
    }
    public static void copyToEntity(
            RecebimentoDTO dto,
            Recebimento target,
            Function<Integer, Lancamento> lancamentoResolver,
            Function<Integer, ContaBancaria> contaBancariaResolver
    )
    {
        if (dto == null || target == null) return;

        Lancamento lancamento = (dto.getLancamentoId() == null) ? null
                : lancamentoResolver.apply(dto.getLancamentoId());

        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null
                : contaBancariaResolver.apply(dto.getContaBancariaId());

        copyToEntity(dto, target, lancamento, contaBancaria);
    }
    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
