package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import com.projetofef.domains.ContaBancaria;

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
    private Integer contaOrigem;

    @NotNull(message = "Id do lançamento é obrigatório")
    private Integer lancamento;

    public PagamentoDTO(Integer id, BigDecimal valorPago, String observacao, Integer contaOrigem, Integer lancamento) {
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

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Integer contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Integer getLancamento() {
        return lancamento;
    }

    public void setLancamento(Integer lancamentoId) {
        this.lancamento = lancamentoId;
    }
}
