package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name="usuario")
@SequenceGenerator(
        name = "seq_usuario",
        sequenceName = "seq_usuario",
        allocationSize = 1
)

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 320)
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate criadoEm = LocalDate.now();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("valor ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("numero ASC")
    private List<ContaBancaria> contaBancarias = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("nome ASC")
    private List<Entidade> entidades = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("nome ASC")
    private List<CentroCusto> centroCustos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("apelido ASC")
    private List<CartaoCredito> cartaoCreditos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String email, LocalDate criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.criadoEm = criadoEm;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getcriadoEm() {
        return criadoEm;
    }

    public void setcriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void setContaBancarias(List<ContaBancaria> contaBancarias) {
        this.contaBancarias = contaBancarias;
    }

    public void setEntidades(List<Entidade> entidades) {
        this.entidades = entidades;
    }

    public void setCentroCustos(List<CentroCusto> centroCustos) {
        this.centroCustos = centroCustos;
    }

    public void setCartaoCreditos(List<CartaoCredito> cartaoCreditos) {
        this.cartaoCreditos = cartaoCreditos;
    }

    public void addLancamento(Lancamento la){
        if (la == null) return;
        lancamentos.add(la);
        la.setUsuario(this);
    }

    public void removeLancamento(Lancamento la){
        if (la == null) return;
        lancamentos.remove(la);
        if (la.getUsuario() == this) la.setUsuario(null);
    }

    public void addContaBancaria(ContaBancaria co){
        if (co == null) return;
        contaBancarias.add(co);
        co.setUsuario(this);
    }

    public void removeContaBancaria(ContaBancaria co){
        if (co == null) return;
        contaBancarias.remove(co);
        if (co.getUsuario() == this) co.setUsuario(null);
    }

    public void addEntidade(Entidade en){
        if (en == null) return;
        entidades.add(en);
        en.setUsuario(this);
    }

    public void removeEntidade(Entidade en){
        if (en == null) return;
        entidades.remove(en);
        if (en.getUsuario() == this) en.setUsuario(null);
    }

    public void addCentroCusto(CentroCusto ce){
        if (ce == null) return;
        centroCustos.add(ce);
        ce.setUsuario(this);
    }

    public void removeCentroCusto(CentroCusto ce){
        if (ce == null) return;
        centroCustos.remove(ce);
        if (ce.getUsuario() == this) ce.setUsuario(null);
    }

    public void addCartaoCredito(CartaoCredito ca){
        if (ca == null) return;
        cartaoCreditos.add(ca);
        ca.setUsuario(this);
    }

    public void removeCartaoCredito(CartaoCredito ca){
        if (ca == null) return;
        cartaoCreditos.remove(ca);
        if (ca.getUsuario() == this) ca.setUsuario(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(criadoEm, usuario.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, criadoEm);
    }
}