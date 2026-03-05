package com.projetofef.mappers;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.Entidade;
import com.projetofef.domains.CentroCusto;
import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.Lancamento;
import com.projetofef.domains.dtos.LancamentoDTO;
import com.projetofef.domains.enums.TipoLancamento;
import com.projetofef.domains.enums.StatusLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class LancamentoMapper {
    private  LancamentoMapper() {}

    public static LancamentoDTO toDto(Lancamento e) {
        if(e == null) return null;

        Integer idDto = e.getId();
        Integer contaBancariaId = (e.getContaBancaria() == null) ? null : e.getContaBancaria().getId();
        Integer usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getId();
        Integer entidadeId = (e.getEntidade() == null) ? null : e.getEntidade().getId();
        Integer centroCustoId = (e.getCentroCusto() == null) ? null : e.getCentroCusto().getId();
        Integer cartaoCreditoId = (e.getCartaoCredito() == null) ? null : e.getCartaoCredito().getId();
        int tipoLancamentoInt = e.getTipoLancamento().getId();
        int statusLancamentoInt = e.getStatusLancamento().getId();

        return new LancamentoDTO(
                idDto,
                e.getTipo(),
                e.getDescricao(),
                e.getValor(),
                e.getMeioPagamento(),
                e.getValorBaixado(),
                contaBancariaId,
                usuarioId,
                entidadeId,
                centroCustoId,
                cartaoCreditoId,
                tipoLancamentoInt,
                statusLancamentoInt
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

    public static Lancamento toEntity(LancamentoDTO dto, ContaBancaria contaBancaria, Usuario usuario, Entidade entidade, CentroCusto centroCusto, CartaoCredito cartaoCredito) {
        if(dto == null) return null;

        Lancamento e = new Lancamento();

        e.setId(dto.getId());
        e.setTipo(trim(dto.getTipo()));
        e.setDescricao(trim(dto.getDescricao()));
        e.setValor(dto.getValor());
        e.setMeioPagamento(trim(dto.getMeioPagamento()));
        e.setValorBaixado(dto.getValorBaixado());
        e.setContaBancaria(contaBancaria);
        e.setUsuario(usuario);
        e.setEntidade(entidade);
        e.setCentroCusto(centroCusto);
        e.setCartaoCredito(cartaoCredito);
        e.setTipoLancamento(TipoLancamento.toEnum(dto.getTipoLancamento()));
        e.setStatusLancamento(StatusLancamento.toEnum(dto.getStatusLancamento()));

        return e;
    }

    public static Lancamento toEntity(LancamentoDTO dto, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, Usuario> usuarioResolver, Function<Integer, Entidade> entidadeResolver, Function<Integer, CentroCusto> centroCustoResolver, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null) return null;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        Entidade entidade = (dto.getEntidadeId() == null) ? null : entidadeResolver.apply(dto.getEntidadeId());
        CentroCusto centroCusto = (dto.getCentroCustoId() == null) ? null : centroCustoResolver.apply(dto.getCentroCustoId());
        CartaoCredito cartaoCredito = (dto.getCartaoCreditoId() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCreditoId());

        return toEntity(dto, contaBancaria, usuario, entidade, centroCusto, cartaoCredito);
    }

    public static void copyToEntity(LancamentoDTO dto, Lancamento target, ContaBancaria contaBancaria, Usuario usuario, Entidade entidade, CentroCusto centroCusto, CartaoCredito cartaoCredito) {
        if(dto == null || target == null) return;

        target.setId(dto.getId());
        target.setTipo(trim(dto.getTipo()));
        target.setDescricao(trim(dto.getDescricao()));
        target.setValor(dto.getValor());
        target.setMeioPagamento(trim(dto.getMeioPagamento()));
        target.setValorBaixado(dto.getValorBaixado());
        target.setContaBancaria(contaBancaria);
        target.setUsuario(usuario);
        target.setEntidade(entidade);
        target.setCentroCusto(centroCusto);
        target.setCartaoCredito(cartaoCredito);
        target.setTipoLancamento(TipoLancamento.toEnum(dto.getTipoLancamento()));
        target.setStatusLancamento(StatusLancamento.toEnum(dto.getStatusLancamento()));
    }

    public static void copyToEntity(LancamentoDTO dto, Lancamento target, Function<Integer, ContaBancaria> contaBancariaResolver, Function<Integer, Usuario> usuarioResolver, Function<Integer, Entidade> entidadeResolver, Function<Integer, CentroCusto> centroCustoResolver, Function<Integer, CartaoCredito> cartaoCreditoResolver) {
        if(dto == null || target == null) return;
        ContaBancaria contaBancaria = (dto.getContaBancariaId() == null) ? null : contaBancariaResolver.apply(dto.getContaBancariaId());
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        Entidade entidade = (dto.getEntidadeId() == null) ? null : entidadeResolver.apply(dto.getEntidadeId());
        CentroCusto centroCusto = (dto.getCentroCustoId() == null) ? null : centroCustoResolver.apply(dto.getCentroCustoId());
        CartaoCredito cartaoCredito = (dto.getCartaoCreditoId() == null) ? null : cartaoCreditoResolver.apply(dto.getCartaoCreditoId());
        copyToEntity(dto, target, contaBancaria, usuario, entidade, centroCusto, cartaoCredito);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
