package com.projetofef.domains;

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
        name = "seq_transferencia",
        sequenceName = "seq_transferencia",
        allocationSize = 1
)
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transferencia")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate data = LocalDate.now();

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 3, nullable = false)
    private BigDecimal valor;

    @Column(length = 255)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    public Transferencia() {
        this.valor = BigDecimal.ZERO;
    }

    public Transferencia(
            Integer id,
            LocalDate data,
            BigDecimal valor,
            String observacao,
            ContaBancaria contaBancaria
    ) {
        this.id = id;
        this.data = data;
        this.valor = valor != null ? valor : BigDecimal.ZERO;
        this.observacao = observacao;
        this.contaBancaria = contaBancaria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transferencia that = (Transferencia) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}