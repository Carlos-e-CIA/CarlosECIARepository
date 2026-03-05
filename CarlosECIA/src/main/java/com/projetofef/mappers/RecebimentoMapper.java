package com.projetofef.mappers;

import com.projetofef.domains.Lancamento;
import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Recebimento;
import com.projetofef.domains.dtos.RecebimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecebimentoMapper {
    private  RecebimentoMapper() {}

    public static RecebimentoDTO toDto(Recebimento r) {
        if(r == null) return null;

        Integer idDto = r.getId();
        Integer lancamentoId = (r.getLancamento() == null) ? null : r.getLancamento().getId();
        Integer contaBancariaId = (r.getContaBancaria() == null) ? null : r.getContaBancaria().getId();

        return new RecebimentoDTO(
                idDto,
                r.getValorRecebimento(),
                r.getObservacao(),
                lancamentoId,
                contaBancariaId
        );
    }

    public static List<RecebimentoDTO> toDtoList(Collection<Recebimento> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(RecebimentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<RecebimentoDTO> toDtoPage(Page<Recebimento> page) {
        List<RecebimentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Recebimento toEntity(RecebimentoDTO dto, Lancamento lancamento, ContaBancaria contaBancaria) {
        if(dto == null) return null;

        Recebimento r = new Recebimento();

        r.setId(dto.getId());
        r.setValorRecebimento(dto.getValorRecebido());
        r.setObservacao(trim(dto.getObservacao()));
        r.setLancamento(lancamento);
        r.setContaBancaria(contaBancaria);

        return r;
    }

    public static Recebimento toEntity(RecebimentoDTO dto, Function<Integer, Lancamento> lancamnetoResolver, Function<Integer, ContaBancaria> contaBancariaResolver) {
        if(dto == null) return null;
        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamnetoResolver.apply(dto.getLancamentoId());
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        return toEntity(dto, lancamento, contaBancaria);
    }

    public static void copyToEntity(RecebimentoDTO dto, Recebimento target, Lancamento lancamento, ContaBancaria contaBancaria) {
        if(dto == null || target == null) return;

        target.setValorRecebimento(dto.getValorRecebido());
        target.setObservacao(dto.getObservacao());
        target.setLancamento(lancamento);
        target.setContaBancaria(contaBancaria);
    }

    public static void copyToEntity(RecebimentoDTO dto, Recebimento target, Function <Integer, Lancamento> lancamentoResolver, Function<Integer, ContaBancaria> contaBancariaResolver) {
        if(dto == null || target == null) return;
        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamentoResolver.apply(dto.getLancamentoId());
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        copyToEntity(dto, target, lancamento, contaBancaria);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
