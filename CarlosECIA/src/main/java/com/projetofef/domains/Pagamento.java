package com.projetofef.carlosecia.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.projetofef.carlosecia.domains.enums.MeioPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pagamento")
@SequenceGenerator(
        name = "seq_pagamento",
        sequenceName = "seq_pagamento",
        allocationSize = 1
)
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pagamento")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    @NotNull(message = "A data do pagamento é obrigatória")
    private LocalDate dataPagamento;

    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor do pagamento deve ser maior que zero")
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valorPago;

    @NotBlank(message = "A observação é obrigatória")
    @Column(name = "observacao", nullable = false, length = 100)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contaBancariaId", nullable = false)
    @JsonBackReference
    private ContaBancaria contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lancamentoId", nullable = false)
    @JsonBackReference
    private Lancamento lancamento;

    public Pagamento() {
    }

    public Pagamento(Integer id, LocalDate dataPagamento, BigDecimal valorPago, String observacao, ContaBancaria contaOrigem, Lancamento lancamento) {
        this.id = id;
        this.dataPagamento = dataPagamento;
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    public ContaBancaria getContaBancaria() {
        return contaOrigem;
    }

    public void setContaBancaria(ContaBancaria contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
