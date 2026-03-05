package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_centroCusto",
        sequenceName = "seq_centroCusto",
        allocationSize = 1
)
public class CentroCusto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_livro")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(name = "codigo", nullable = false, length = 20, unique = true)
    private String codigo;

    @NotNull
    @Column(nullable = false, length = 1)
    private Integer ativo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "centroCusto",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("descricao ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    public CentroCusto() {
    }

    public CentroCusto(Integer id, String nome, String codigo, Integer ativo, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.ativo = ativo;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
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
        la.setCentroCusto(this);
    }

    public void removeLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.remove(la);
        if(la.getCentroCusto() == this) la.setCentroCusto(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CentroCusto that = (CentroCusto) o;
        return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo);
    }
}