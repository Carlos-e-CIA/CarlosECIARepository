package com.projetofef.mappers;

import com.projetofef.domains.*;
import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.domains.enums.TipoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LancamentoMapper {
    private  LancamentoMapper() {}

    public static LancamentoDTO toDto(Lancamento l) {
        if(l == null) return null;

        Integer idDto = l.getId();
        Integer usuario = (l.getUsuario() == null) ? null : l.getUsuario().getId();
        Integer entidade = (l.getEntidade() == null) ? null : l.getEntidade().getId();
        Integer centroCusto = (l.getCentroCusto() == null) ? null : l.getCentroCusto().getId();
        Integer contaBancaria = (l.getContaBancaria() == null) ? null : l.getContaBancaria().getId();
        Integer cartaoCredito = (l.getCartaoCredito() == null) ? null : l.getCartaoCredito().getId();
        int meioPagamento = l.getMeioPagamento().getId();
        int tipoLancamento = l.getTipoLancamento().getId();
        int statusLancamento = l.getStatusLancamento().getId();

        return new LancamentoDTO(
                idDto,
                l.getDescricao(),
                l.getValor(),
                meioPagamento,
                l.getValorBaixado(),
                usuario,
                entidade,
                centroCusto,
                contaBancaria,
                cartaoCredito,
                tipoLancamento,
                statusLancamento
        );
    }

    public static List<LancamentoDTO> toDtoList(Collection<Lancamento> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(LancamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<LancamentoDTO> toDtoPage(Page<Lancamento> page) {
        List<LancamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Lancamento toEntity(LancamentoDTO dto, Usuario usuario, Entidade entidade, CentroCusto centroCusto, ContaBancaria contaBancaria, CartaoCredito cartaoCredito) {
        if(dto == null) return null;

        Lancamento l = new Lancamento();

        l.setId(dto.getId());
        l.setDescricao(trim(dto.getDescricao()));
        l.setValor(dto.getValor());
        l.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));
        l.setValorBaixado(dto.getValorBaixado());
        l.setUsuario(usuario);
        l.setEntidade(entidade);
        l.setCentroCusto(centroCusto);
        l.setContaBancaria(contaBancaria);
        l.setCartaoCredito(cartaoCredito);
        l.setTipoLancamento(TipoLancamento.toEnum(dto.getTipoLancamento()));
        l.setStatusLancamento(StatusLancamento.toEnum(dto.getStatusLancamento()));

        return l;
    }

    public static Lancamento toEntity(LancamentoDTO dto, Function<Integer, Usuario> usuarioResolver, Function<Integer, Entidade> entidadeResolver, Function<Integer, CentroCusto> centroCustoResolver, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null) return null;
        Usuario usuario = (dto.getUsuario() == null) ? null : usuarioResolver.apply(dto.getUsuario());
        Entidade entidade = (dto.getEntidade() == null) ? null : entidadeResolver.apply(dto.getEntidade());
        CentroCusto centroCusto = (dto.getCentroCusto() == null) ? null : centroCustoResolver.apply(dto.getCentroCusto());
        ContaBancaria contaBancaria = (dto.getContaBancaria() == null) ? null : contaBancariaResolver.apply(dto.getContaBancaria());
        CartaoCredito cartaoCredito = (dto.getCartaoCredito() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCredito());
        return toEntity(dto, usuario, entidade, centroCusto, contaBancaria, cartaoCredito);
    }

    public static void copyToEntity(LancamentoDTO dto, Lancamento target, Usuario usuario, Entidade entidade, CentroCusto centroCusto, ContaBancaria contaBancaria, CartaoCredito cartaoCredito) {
        if(dto == null || target == null) return;

        target.setDescricao(trim(dto.getDescricao()));
        target.setValor(dto.getValor());
        target.setMeioPagamento(MeioPagamento.toEnum(dto.getMeioPagamento()));
        target.setValorBaixado(dto.getValorBaixado());
        target.setUsuario(usuario);
        target.setEntidade(entidade);
        target.setCentroCusto(centroCusto);
        target.setContaBancaria(contaBancaria);
        target.setCartaoCredito(cartaoCredito);
        target.setTipoLancamento(TipoLancamento.toEnum(dto.getTipoLancamento()));
        target.setStatusLancamento(StatusLancamento.toEnum(dto.getStatusLancamento()));
    }

    public static void copyToEntity (LancamentoDTO dto, Lancamento target, Function<Integer, Usuario> usuarioResolver, Function<Integer, Entidade> entidadeResolver, Function<Integer, CentroCusto> centroCustoResolver, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null || target == null) return;
        Usuario usuario = (dto.getUsuario() == null) ? null : usuarioResolver.apply(dto.getUsuario());
        Entidade entidade = (dto.getEntidade() == null) ? null : entidadeResolver.apply(dto.getEntidade());
        CentroCusto centroCusto = (dto.getCentroCusto() == null) ? null : centroCustoResolver.apply(dto.getCentroCusto());
        ContaBancaria contaBancaria = (dto.getContaBancaria() == null) ? null : contaBancariaResolver.apply(dto.getContaBancaria());
        CartaoCredito cartaoCredito = (dto.getCartaoCredito() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCredito());
        copyToEntity(dto, target, usuario, entidade, centroCusto, contaBancaria, cartaoCredito);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
