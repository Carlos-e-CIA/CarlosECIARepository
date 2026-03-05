package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_investimento",
        sequenceName = "seq_investimento",
        allocationSize = 1
)
public class Investimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_investimento")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotBlank
    @Column(name = "isbn", nullable = false, length = 13, unique = true)
    private String codigo;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal juros;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "investimento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("historico ASC")
    private List<MovimentoConta> movimentoContas = new ArrayList<>();

    public Investimento() {
        this.valor = BigDecimal.ZERO;
        this.juros = BigDecimal.ZERO;
    }

    public Investimento(Integer id, String tipo, String codigo, BigDecimal valor, BigDecimal juros, ContaBancaria contaBancaria) {
        this.id = id;
        this.tipo = tipo;
        this.codigo = codigo;
        this.valor = valor != null ? valor : BigDecimal.ZERO;
        this.juros = juros != null ? juros : BigDecimal.ZERO;
        this.contaBancaria = contaBancaria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public void setMovimentoContas(List<MovimentoConta> movimentoContas) {
        this.movimentoContas = movimentoContas;
    }

    public void addMovimentoConta(MovimentoConta mo) {
        if(movimentoContas == null) return;
        movimentoContas.add(mo);
        mo.setInvestimento(this);
    }

    public void removeMovimentoConta(MovimentoConta mo) {
        if(movimentoContas == null) return;
        movimentoContas.remove(mo);
        if(mo.getInvestimento() == this) mo.setInvestimento(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Investimento that = (Investimento) o;
        return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo);
    }
}