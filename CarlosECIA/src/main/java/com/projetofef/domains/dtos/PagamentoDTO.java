package com.projetofef.carlosecia.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class PagamentoDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "Valor pago é obrigatório")
    @Digits(integer = 12, fraction = 2)
    @Positive(message = "O valor do pagamento deve ser maior que zero")
    @PositiveOrZero(message = "Valor da Compra não pode ser negativo")
    private BigDecimal valorPago;

    @NotBlank(message = "A observação é obrigatória")
    @Size(max = 100)
    private String observacao;

    @NotNull(message = "Id da conta origem é obrigatório")
    private ContaBancaria contaOrigem;

    @NotNull(message = "Id do lançamento é obrigatório")
    private Lancamento lancamento;

    public PagamentoDTO(Integer id, BigDecimal valorPago, String observacao, ContaBancaria contaOrigem, Lancamento lancamento) {
        this.id = id;
        this.valorPago = valorPago;
        this.observacao = observacao;
        this.contaOrigem = contaOrigem;
        this.lancamento = lancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ContaBancaria getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(ContaBancaria contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }
}
