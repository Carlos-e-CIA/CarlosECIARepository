package com.projetofef.domains.dtos;

import com.projetofef.domains.*;
import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.domains.enums.TipoLancamento;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class LancamentoDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = LancamentoDTO.Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = LancamentoDTO.Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Descricao é obrigatório")
    @Size(max = 300, message = "Descricao deve ter, no máximo, 300 caracteres")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 12, fraction = 2)
    @Positive(message = "O valor deve ser maior que zero")
    @PositiveOrZero(message = " O valor não pode ser negativo")
    private BigDecimal valor;

    @Min(value = 0)
    @Max(value = 3)
    private int meioPagamento;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 12, fraction = 2)
    @Positive(message = "O valor deve ser maior que zero")
    @PositiveOrZero(message = " O valor não pode ser negativo")
    private BigDecimal valorBaixado;

    @NotNull(message = "Id do usuário é obrigatório")
    private Integer usuario;

    @NotNull(message = "Id da entidade é obrigatório")
    private Integer entidade;

    @NotNull(message = "Id do centroCusto é obrigatório")
    private Integer centroCusto;

    @NotNull(message = "Id da conta bancária é obrigatório")
    private Integer contaBancaria;

    @NotNull(message = "Id do cartão de crédito é obrigatório")
    private Integer cartaoCredito;

    @Min(value = 0)
    @Max(value = 1)
    private int tipoLancamento;

    @Min(value = 0)
    @Max(value = 3)
    private int statusLancamento;

    public LancamentoDTO(Integer id, String descricao, BigDecimal valor, int meioPagamento, BigDecimal valorBaixado, Integer usuario, Integer entidade, Integer centroCusto, Integer contaBancaria, Integer cartaoCredito, int tipoLancamento, int statusLancamento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.meioPagamento = meioPagamento;
        this.valorBaixado = valorBaixado;
        this.usuario = usuario;
        this.entidade = entidade;
        this.centroCusto = centroCusto;
        this.contaBancaria = contaBancaria;
        this.cartaoCredito = cartaoCredito;
        this.tipoLancamento = tipoLancamento;
        this.statusLancamento = statusLancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(int meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getEntidade() {
        return entidade;
    }

    public void setEntidade(Integer entidade) {
        this.entidade = entidade;
    }

    public Integer getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(Integer centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Integer getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(Integer contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public Integer getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(Integer cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public int getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(int tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public int getStatusLancamento() {
        return statusLancamento;
    }

    public void setStatusLancamento(int statusLancamento) {
        this.statusLancamento = statusLancamento;
    }
}
