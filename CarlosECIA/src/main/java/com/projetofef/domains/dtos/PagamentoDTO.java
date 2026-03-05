package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class PagamentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = PagamentoDTO.Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = PagamentoDTO.Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "ValorPago é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "ValorPago deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "ValorPago não pode ser negativo")
    private BigDecimal valorPago;

    @Size(max = 14, message = "Observacao deve ter, no máximo, 120 caracteres")
    private String observacao;

    @NotNull(message = "Lancamento é obrigatório")
    private Integer lancamentoId;

    @NotNull(message = "ContaBancaria é obrigatório")
    private Integer contaBancariaId;

    @Min(value = 0, message = "MeioPagamento inválido: use 0 (CONTA), 1 (CARTAO), 2 (DINHEIRO), 3 (PIX)")
    @Max(value = 3, message = "MeioPagamento inválido: use 0 (CONTA), 1 (CARTAO), 2 (DINHEIRO), 3 (PIX)")
    private int MeioPagamento;

    public PagamentoDTO() {
    }

    public PagamentoDTO(Integer id, BigDecimal valorPago, String observacao, Integer lancamentoId, Integer contaBancariaId, int meioPagamento) {
        this.id = id;
        this.valorPago = valorPago;
        this.observacao = observacao;
        this.lancamentoId = lancamentoId;
        this.contaBancariaId = contaBancariaId;
        MeioPagamento = meioPagamento;
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

    public Integer getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Integer lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public Integer getContaBancariaId() {
        return contaBancariaId;
    }

    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }

    public int getMeioPagamento() {
        return MeioPagamento;
    }

    public void setMeioPagamento(int meioPagamento) {
        MeioPagamento = meioPagamento;
    }
}
