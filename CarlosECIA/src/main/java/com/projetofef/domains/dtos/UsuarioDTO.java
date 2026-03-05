package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

public class UsuarioDTO {
    public interface Create{}
    public interface Update{}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 320, message = "E-mail deve ter no máximo 320 caracteres")
    private String email;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id = " + id +
                ", nome = '" + nome + '\'' +
                ", email = " + email +
                '}';
    }
}