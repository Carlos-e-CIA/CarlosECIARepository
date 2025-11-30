package com.projetofef.carlosecia.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="contaBancaria")
@SequenceGenerator(
        name = "seq_contaBancaria",
        sequenceName = "seq_contaBancaria",
        allocationSize = 1
)
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contaBancaria")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String instituicao;

    @NotBlank
    @Column(nullable = false, length = 5)
    private String agencia;

    @NotNull
    @Column(nullable = false, length = 13)
    private Integer numero;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String apelido;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataSaldoInicial = LocalDate.now();

    @NotBlank
    private char ativa;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "contaBancaria",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("historico ASC")
    private List<MovimentoConta> movimentoContas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "contaBancaria",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("descricao ASC")
    private List<Lancamento> lancamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "contaBancaria",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("observacao ASC")
    private List<Pagamento> pagamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "contaBancaria",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("observacao ASC")
    private List<Recebimento> recebimentos = new ArrayList<>();

    public ContaBancaria(Integer id, String instituicao, String agencia, Integer numero, String apelido, BigDecimal saldoInicial, LocalDate dataSaldoInicial, char ativa, Usuario usuario) {
        this.id = id;
        this.instituicao = instituicao;
        this.agencia = agencia;
        this.numero = numero;
        this.apelido = apelido;
        this.saldoInicial = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
        this.dataSaldoInicial = dataSaldoInicial;
        this.ativa = ativa;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public LocalDate getDataSaldoInicial() {
        return dataSaldoInicial;
    }

    public void setDataSaldoInicial(LocalDate dataSaldoInicial) {
        this.dataSaldoInicial = dataSaldoInicial;
    }

    public char getAtiva() {
        return ativa;
    }

    public void setAtiva(char ativa) {
        this.ativa = ativa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMovimentoContas(List<MovimentoConta> movimentoContas) {
        this.movimentoContas = movimentoContas;
    }

    public void addMovimentoConta(MovimentoConta mo) {
        if(movimentoContas == null) return;
        movimentoContas.add(mo);
        mo.setContaBancaria(this);
    }

    public void removeMovimentoConta(MovimentoConta mo) {
        if(movimentoContas == null) return;
        movimentoContas.remove(mo);
        if(mo.getContaBancaria() == this) mo.setContaBancaria(null);
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void addLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.add(la);
        la.setContaBancaria(this);
    }

    public void removeLancamento(Lancamento la) {
        if(lancamentos == null) return;
        lancamentos.remove(la);
        if(la.getContaBancaria() == this) la.setContaBancaria(null);
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addPagamento(Pagamento pa) {
        if(pagamentos == null) return;
        pagamentos.add(pa);
        pa.setContaBancaria(this);
    }

    public void removePagamento(Pagamento pa) {
        if(pagamentos == null) return;
        pagamentos.remove(pa);
        if(pa.getContaBancaria() == this) pa.setContaBancaria(null);
    }

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
    }

    public void addRecebimento(Recebimento re) {
        if(recebimentos == null) return;
        recebimentos.add(re);
        re.setContaBancaria(this);
    }

    public void removeRecebimento(Recebimento re) {
        if(recebimentos == null) return;
        recebimentos.remove(re);
        if(re.getContaBancaria() == this) re.setContaBancaria(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
