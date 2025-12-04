package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UsuarioDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 120, message = "O nome deve conter no máximo 120 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    @Size(max = 180)
    private String email;

    @PastOrPresent(message = "A data de criação não pode ser futura")
    private LocalDate criadoEm;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer id, String nome, String email, LocalDate criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.criadoEm = criadoEm;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDate criadoEm) { this.criadoEm = criadoEm; }
}