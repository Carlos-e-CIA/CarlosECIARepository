package com.projetofef.carlosecia.domains.enums;

public enum TipoLancamento {
    PAGAR(0, "CONTA"), RECEBER(1, "CARTAO");

    private Integer id;
    private String descricao;

    TipoLancamento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static TipoLancamento toEnum(String descricao) {
        if(descricao == null) return null;
        for (TipoLancamento tipoLancamento : TipoLancamento.values()) {
            if (descricao.equals(tipoLancamento.getDescricao())) {
                return tipoLancamento;
            }
        }
        throw new IllegalArgumentException("TipoLancamento Inválido!");
    }
}