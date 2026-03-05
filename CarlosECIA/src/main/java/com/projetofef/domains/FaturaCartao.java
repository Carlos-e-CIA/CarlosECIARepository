package com.projetofef.domains;

import com.projetofef.domains.enums.StatusFatura;
import com.projetofef.infra.StatusFaturaConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_faturaCartao",
        sequenceName = "seq_faturaCartao",
        allocationSize = 1
)
public class FaturaCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_faturaCartao")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate competencia = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataFechamento = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataVencimento = LocalDate.now();

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCartaoCredito", nullable = false)
    @JsonBackReference
    private CartaoCredito cartaoCredito;

    @Convert(converter = StatusFaturaConverter.class)
    @Column(name = "statusFatura", nullable = false)
    private StatusFatura statusFatura;

    public FaturaCartao() {
        this.valorTotal = BigDecimal.ZERO;
    }

    public FaturaCartao(Integer id, LocalDate competencia, LocalDate dataFechamento, LocalDate dataVencimento, BigDecimal valorTotal, CartaoCredito cartaoCredito, StatusFatura statusFatura) {
        this.id = id;
        this.competencia = competencia;
        this.dataFechamento = dataFechamento;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
        this.cartaoCredito = cartaoCredito;
        this.statusFatura = statusFatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCompetencia() {
        return competencia;
    }

    public void setCompetencia(LocalDate competencia) {
        this.competencia = competencia;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public StatusFatura getStatusFatura() {
        return statusFatura;
    }

    public void setStatusFatura(StatusFatura statusFatura) {
        this.statusFatura = statusFatura;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FaturaCartao that = (FaturaCartao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}