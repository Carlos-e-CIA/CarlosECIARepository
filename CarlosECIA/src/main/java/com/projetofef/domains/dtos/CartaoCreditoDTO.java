package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

public class CartaoCreditoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = ContaBancariaDTO.Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = ContaBancariaDTO.Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Bandeira é obrigatória")
    @Size(max = 30, message = "Bandeira deve ter, no máximo, 60 caracteres")
    private String bandeira;

    @NotBlank(message = "Emissor é obrigatório")
    @Size(max = 60, message = "Emissor deve ter, no máximo, 60 caracteres")
    private String emissor;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(max = 60, message = "Apelido deve ter, no máximo, 60 caracteres")
    private String apelido;

    @NotBlank(message = "Ativo é obrigatório")
    private char ativo;

    public CartaoCreditoDTO() {}

    public CartaoCreditoDTO(Integer id, String bandeira, String emissor, String apelido, char ativo) {
        this.id = id;
        this.bandeira = bandeira;
        this.emissor = emissor;
        this.apelido = apelido;
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

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }
}
