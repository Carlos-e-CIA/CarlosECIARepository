package com.projetofef.carlosecia.domains.enums;

public enum MeioPagamento {
    CONTA(0, "CONTA"), CARTAO(1, "CARTAO"), DINHEIRO(2, "DINHEIRO"), PIX(3, "PIX");

    private Integer id;
    private String descricao;

    MeioPagamento(Integer id, String descricao) {
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

    public static MeioPagamento toEnum(String descricao) {
        if(descricao == null) return null;
        for (MeioPagamento meioPagamento : MeioPagamento.values()) {
            if (descricao.equals(meioPagamento.getDescricao())) {
                return meioPagamento;
            }
        }
        throw new IllegalArgumentException("MeioPagamento Inválido!");
    }
}