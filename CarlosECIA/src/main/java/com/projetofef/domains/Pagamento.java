package com.projetofef.domains;

import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.infra.MeioPagamentoConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
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
    private LocalDate dataPagamento = LocalDate.now();

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valorPago;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLancamento", nullable = false)
    @JsonBackReference
    private Lancamento lancamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @Convert(converter = MeioPagamentoConverter.class)
    @Column(name = "meioPagamento", nullable = false)
    private MeioPagamento meioPagamento;

    public Pagamento() {
        this.valorPago = BigDecimal.ZERO;
    }

    public Pagamento(Integer id, LocalDate dataPagamento, BigDecimal valorPago, String observacao, Lancamento lancamento, ContaBancaria contaBancaria, MeioPagamento meioPagamento) {
        this.id = id;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago != null ? valorPago : BigDecimal.ZERO;
        this.observacao = observacao;
        this.lancamento = lancamento;
        this.contaBancaria = contaBancaria;
        this.meioPagamento = meioPagamento;
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

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
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