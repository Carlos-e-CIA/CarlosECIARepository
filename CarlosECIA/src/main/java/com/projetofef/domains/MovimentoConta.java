package com.projetofef.domains;

import com.projetofef.domains.enums.TipoTransacao;
import com.projetofef.infra.TipoTransacaoConverter;
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
        name = "seq_movimentoConta",
        sequenceName = "seq_movimentoConta",
        allocationSize = 1
)
public class MovimentoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentoConta")
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataMovimento = LocalDate.now();

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotBlank
    @Column(nullable = false, length = 400)
    private String historico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idInvestimento", nullable = false)
    @JsonBackReference
    private Investimento investimento;

    @Convert(converter = TipoTransacaoConverter.class)
    @Column(name = "tipoTransacao", nullable = false)
    private TipoTransacao tipoTransacao;

    public MovimentoConta() {
    }

    public MovimentoConta(Integer id, LocalDate dataMovimento, String tipo, BigDecimal valor, String historico, ContaBancaria contaBancaria, Investimento investimento, TipoTransacao tipoTransacao) {
        this.id = id;
        this.dataMovimento = dataMovimento;
        this.tipo = tipo;
        this.valor = valor;
        this.historico = historico;
        this.contaBancaria = contaBancaria;
        this.investimento = investimento;
        this.tipoTransacao = tipoTransacao;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataMovimento() {
        return dataMovimento;
    }
    public void setDataMovimento(LocalDate dataMovimento) {
        this.dataMovimento = dataMovimento;
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

    public String getHistorico() {
        return historico;
    }
    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }
    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public Investimento getInvestimento() {
        return investimento;
    }

    public void setInvestimento(Investimento investimento) {
        this.investimento = investimento;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }
    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovimentoConta that = (MovimentoConta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}