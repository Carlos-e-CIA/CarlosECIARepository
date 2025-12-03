package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="cartaoCredito")
@SequenceGenerator(
        name = "seq_cartaoCredito",
        sequenceName = "seq_cartaoCredito",
        allocationSize = 1
)
public class CartaoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cartaoCredito")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String bandeira;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String emissor;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String apelido;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate fechamentoFaturaDia;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate vencimentoFaturaDia;

    @NotBlank
    private char ativo;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "cartaoCredito",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("competencia ASC")
    private List<FaturaCartao> faturaCartaos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "cartaoCredito",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("descricao ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    public CartaoCredito() {
    }

    public CartaoCredito(Integer id, String bandeira, String emissor, String apelido, LocalDate fechamentoFaturaDia, LocalDate vencimentoFaturaDia, char ativo) {
        this.id = id;
        this.bandeira = bandeira;
        this.emissor = emissor;
        this.apelido = apelido;
        this.fechamentoFaturaDia = fechamentoFaturaDia;
        this.vencimentoFaturaDia = vencimentoFaturaDia;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public LocalDate getFechamentoFaturaDia() {
        return fechamentoFaturaDia;
    }

    public void setFechamentoFaturaDia(LocalDate fechamentoFaturaDia) {
        this.fechamentoFaturaDia = fechamentoFaturaDia;
    }

    public LocalDate getVencimentoFaturaDia() {
        return vencimentoFaturaDia;
    }

    public void setVencimentoFaturaDia(LocalDate vencimentoFaturaDia) {
        this.vencimentoFaturaDia = vencimentoFaturaDia;
    }

    public char getAtivo() {
        return ativo;
    }

    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }

    public List<FaturaCartao> getFaturaCartaos() {
        return faturaCartaos;
    }

    public void setFaturaCartaos(List<FaturaCartao> faturaCartaos) {
        this.faturaCartaos = faturaCartaos;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartaoCredito that = (CartaoCredito) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
