package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class LancamentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 120, message = "Tipo deve ter, no máximo, 120 caracteres")
    private String tipo;

    @NotBlank(message = "Descricao é obrigatório")
    @Size(max = 120, message = "Descricao deve ter, no máximo, 120 caracteres")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @NotBlank(message = "MeioPagamento é obrigatório")
    @Size(max = 120, message = "MeioPagamento deve ter, no máximo, 120 caracteres")
    private String meioPagamento;

    @NotNull(message = "ValorBaixado é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "ValorBaixado deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "ValorBaixado não pode ser negativo")
    private BigDecimal valorBaixado;

    @NotNull(message = "ContaBancaria é obrigatório")
    private Integer contaBancariaId;

    @NotNull(message = "Usuario é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "Entidade é obrigatório")
    private Integer entidadeId;

    @NotNull(message = "CentroCusto é obrigatório")
    private Integer centroCustoId;

    @NotNull(message = "CartaoCredito é obrigatório")
    private Integer cartaoCreditoId;

    @Min(value = 0, message = "TipoLancamento inválido: use 0 (PAGAR), 1 (RECEBER)")
    @Max(value = 1, message = "TipoLancamento inválido: use 0 (PAGAR), 1 (RECEBER)")
    private int TipoLancamento;

    @Min(value = 0, message = "StatusLancamento inválido: use 0 (PENDENTE), 1 (BAIXADO), 2 (PARCIAL), 3 (CANCELADO)")
    @Max(value = 3, message = "StatusLancamento inválido: use 0 (PENDENTE), 1 (BAIXADO), 2 (PARCIAL), 3 (CANCELADO)")
    private int StatusLancamento;

    public LancamentoDTO() {
    }

    public LancamentoDTO(Integer id, String tipo, String descricao, BigDecimal valor, String meioPagamento, BigDecimal valorBaixado, Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, int tipoLancamento, int statusLancamento) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.meioPagamento = meioPagamento;
        this.valorBaixado = valorBaixado;
        this.contaBancariaId = contaBancariaId;
        this.usuarioId = usuarioId;
        this.entidadeId = entidadeId;
        this.centroCustoId = centroCustoId;
        this.cartaoCreditoId = cartaoCreditoId;
        TipoLancamento = tipoLancamento;
        StatusLancamento = statusLancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(String meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
    }

    public Integer getContaBancariaId() {
        return contaBancariaId;
    }

    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Integer entidadeId) {
        this.entidadeId = entidadeId;
    }

    public Integer getCentroCustoId() {
        return centroCustoId;
    }

    public void setCentroCustoId(Integer centroCustoId) {
        this.centroCustoId = centroCustoId;
    }

    public Integer getCartaoCreditoId() {
        return cartaoCreditoId;
    }

    public void setCartaoCreditoId(Integer cartaoCreditoId) {
        this.cartaoCreditoId = cartaoCreditoId;
    }

    public int getTipoLancamento() {
        return TipoLancamento;
    }

    public void setTipoLancamento(int tipoLancamento) {
        TipoLancamento = tipoLancamento;
    }

    public int getStatusLancamento() {
        return StatusLancamento;
    }

    public void setStatusLancamento(int statusLancamento) {
        StatusLancamento = statusLancamento;
    }
}