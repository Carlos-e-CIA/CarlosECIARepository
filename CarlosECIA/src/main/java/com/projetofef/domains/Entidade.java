package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_entidade",
        sequenceName = "seq_entidade",
        allocationSize = 1
)
public class Entidade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidade")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(name = "documento", nullable = false, length = 14, unique = true)
    private String documento;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "entidade",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("descricao ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    public Entidade() {
    }

    public Entidade(Integer id, String nome, String documento, String tipo, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        la.setEntidade(this);
    }

    public void removeLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.remove(la);
        if(la.getEntidade() == this) la.setEntidade(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entidade entidade = (Entidade) o;
        return Objects.equals(id, entidade.id) && Objects.equals(documento, entidade.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento);
    }
}