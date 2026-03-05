package com.projetofef.domains;

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
        name = "seq_recebimento",
        sequenceName = "seq_recebimento",
        allocationSize = 1
)

public class Recebimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recebimento")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataRecebimento = LocalDate.now();

    @NotNull
    @Digits(integer = 15, fraction = 3)
    @Column(precision = 18, scale = 3, nullable = false)
    private BigDecimal valorRecebimento;

    @NotBlank
    @Column(nullable = false, length = 400)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contaBancariaId", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lancamentoId", nullable = false)
    @JsonBackReference
    private Lancamento lancamento;

    public Recebimento() {
    }

    public Recebimento(Integer id, LocalDate dataRecebimento, BigDecimal valorRecebimento, String observacao, ContaBancaria contaBancaria, Lancamento lancamento) {
        this.id = id;
        this.dataRecebimento = dataRecebimento;
        this.valorRecebimento = valorRecebimento;
        this.observacao = observacao;
        this.contaBancaria = contaBancaria;
        this.lancamento = lancamento;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getValorRecebimento() {
        return valorRecebimento;
    }

    public void setValorRecebimento(BigDecimal valorRecebimento) {
        this.valorRecebimento = valorRecebimento;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recebimento that = (Recebimento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
