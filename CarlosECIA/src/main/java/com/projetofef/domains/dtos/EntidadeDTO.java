package com.projetofef.domains.dtos;

import com.projetofef.domains.Usuario;
import jakarta.validation.constraints.*;

public class EntidadeDTO {
    public interface Create{}
    public interface Update{}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "Usuário é obrigatório")
    private Usuario usuarioId;

    @NotBlank(message = "O nome da entidade é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O documento é obrigatório")
    @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
    private String documento;

    @NotBlank(message = "O tipo é obrigatório")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres")
    private String tipo;

    public EntidadeDTO(Integer id, Usuario usuarioId, String nome, String documento, String tipo) {
        this.id = id;
        this.usuario = usuarioId;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Usuario getUsuarioId() {return usuarioId;}
    public void setUsuarioId(Usuario usuarioId) {this.usuarioId = usuarioId;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDocumento() {return documento;}
    public void setDocumento(String documento) {this.documento = documento;}


    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

}
