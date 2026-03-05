package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
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
    @Column(nullable = false, length = 50)
    private String bandeira;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String emissor;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String apelido;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate fechamentoFaturaDia = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate vencimentoFaturaDia = LocalDate.now();

    @NotNull
    @Column(nullable = false, length = 1)
    private Character ativo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "cartaoCredito",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("dataCompetencia ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "cartaoCredito",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("competencia ASC")
    private List<FaturaCartao> faturaCartaos = new ArrayList<>();

    public CartaoCredito() {
    }

    public CartaoCredito(Integer id, String bandeira, String apelido, String emissor, LocalDate fechamentoFaturaDia, LocalDate vencimentoFaturaDia, Character ativo, Usuario usuario) {
        this.id = id;
        this.bandeira = bandeira;
        this.apelido = apelido;
        this.emissor = emissor;
        this.fechamentoFaturaDia = fechamentoFaturaDia;
        this.vencimentoFaturaDia = vencimentoFaturaDia;
        this.ativo = ativo;
        this.usuario = usuario;
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

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void addLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.add(la);
        la.setCartaoCredito(this);
    }

    public void removeLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.remove(la);
        if(la.getCartaoCredito() == this) la.setCartaoCredito(null);
    }

    public void setFaturaCartaos(List<FaturaCartao> faturaCartaos) {
        this.faturaCartaos = faturaCartaos;
    }

    public void addFaturaCartao(FaturaCartao fa) {
        if(faturaCartaos == null) return;
        faturaCartaos.add(fa);
        fa.setCartaoCredito(this);
    }

    public void removeFaturaCartao(FaturaCartao fa) {
        if(faturaCartaos == null) return;
        faturaCartaos.remove(fa);
        if(fa.getCartaoCredito() == this) fa.setCartaoCredito(null);
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