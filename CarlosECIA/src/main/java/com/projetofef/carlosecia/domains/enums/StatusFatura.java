package com.projetofef.carlosecia.domains.enums;

public enum StatusFatura {
    ABERTA(0, "ABERTA"), FECHADA(1, "FECHADA"), PAGA(2, "PAGA");

    private Integer id;
    private String descricao;

    StatusFatura(Integer id, String descricao) {
       this.id = id;
       this.descricao = descricao;
    }

    public Integer getId(){return id;}

    public void setId(Integer id){this.id = id;}

    public String getDescricao(){return descricao;}

    public void setDescricao(String descricao){this.descricao = descricao;}

    public static StatusFatura toEnum(String descricao) {
        if (descricao == null) return null;
        for(StatusFatura statusFatura : StatusFatura.values()) {
            if (descricao.equals(statusFatura.getDescricao())) {
                return statusFatura;
            }
        }
        throw new IllegalArgumentException("StatusFatura Inválido!");
    }
}



