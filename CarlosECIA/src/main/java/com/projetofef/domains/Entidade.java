package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "entidade")
@SequenceGenerator(
        name = "seq_entidade",
        sequenceName = "seq_entidade",
        allocationSize = 1
)

public class Entidade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidade")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false)
    @JsonBackReference
    private Usuario usuarioId;

    @NotBlank (message = "O nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O documento é obrigatório")
    @Column(nullable = false, length = 20)
    private String documento;

    @NotBlank(message = "O tipo é obrigatório")
    @Column(nullable = false, length = 50)
    private String tipo;


    public Entidade() {
    }

    public Entidade(Integer id, Usuario usuarioId, String nome, String documento, String tipo) {
        this.id = id;
        this.usuario = usuarioId;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Usuario getUsuario() {return usuarioId;}
    public void setUsuario(Usuario usuarioId) {this.usuarioId = usuarioId;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDocumento() {return documento;}
    public void setDocumento(String documento) {this.documento = documento;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidade entidade = (Entidade) o;
        return Objects.equals(id, entidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
