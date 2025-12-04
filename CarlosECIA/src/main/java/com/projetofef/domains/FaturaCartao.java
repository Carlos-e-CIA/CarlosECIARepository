package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.projetofef.domains.enums.StatusFatura;
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
        name = "seq_faturacartao",
        sequenceName = "seq_faturacartao",
        allocationSize = 1
)
public class FaturaCartao {

    @Id
    @GeneratedValue(generator = "seq_faturacartao")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String competencia;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataFechamento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataVencimento;

    @NotNull
    @Digits(integer = 15, fraction = 3)
    @Column(precision = 18, scale = 3, nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cartaoCreditoId", nullable = false)
    @JsonBackReference
    private CartaoCredito cartaoCredito;

    @Convert(converter = StatusFatura.class)
    @Column(name = "statusFatura", nullable = false)
    private StatusFatura statusFatura;

    public FaturaCartao() {
    }

    public FaturaCartao(Integer id, String competencia, LocalDate dataFechamento, LocalDate dataVencimento, BigDecimal valorTotal, CartaoCredito cartaoCredito, StatusFatura statusFatura) {
        this.id = id;
        this.competencia = competencia;
        this.dataFechamento = dataFechamento;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.cartaoCredito = cartaoCredito;
        this.statusFatura = statusFatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
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
        if (!(o instanceof FaturaCartao that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
