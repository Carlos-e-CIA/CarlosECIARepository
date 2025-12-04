package com.projetofef.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.domains.enums.TipoLancamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@SequenceGenerator(
        name = "seq_lancamento",
        sequenceName = "seq_lancamento",
        allocationSize = 1
)
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lancamento")
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 300)
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor do lancamento deve ser maior que zero")
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    @NotNull(message = "A data é obrigatória")
    private LocalDate dataCompetencia;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    @NotNull(message = "A data vencimento é obrigatória")
    private LocalDate dataVencimento;

    @Convert(converter = MeioPagamento.class)
    @Column(name = "meioPagamento", nullable = false)
    private MeioPagamento meioPagamento;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valorBaixado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEntidade", nullable = false)
    @JsonBackReference
    private Entidade entidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCentroCusto", nullable = false)
    @JsonBackReference
    private CentroCusto centroCusto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCartaoCredito", nullable = false)
    @JsonBackReference
    private CartaoCredito cartaoCredito;

    @Convert(converter = TipoLancamento.class)
    @Column(name = "tipoLancamento", nullable = false)
    private TipoLancamento tipoLancamento;

    @Convert(converter = StatusLancamento.class)
    @Column(name = "statusLancamento", nullable = false)
    private StatusLancamento statusLancamento;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "lancamento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("id")
    private List<Pagamento> pagamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "lancamento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("id")
    private List<Recebimento> recebimentos = new ArrayList<>();

    public Lancamento() {
    }

    public Lancamento(Integer id, String descricao, BigDecimal valor, LocalDate dataCompetencia, LocalDate dataVencimento, MeioPagamento meioPagamento, BigDecimal valorBaixado, Usuario usuario, Entidade entidade, CentroCusto centroCusto, ContaBancaria contaBancaria, CartaoCredito cartaoCredito, TipoLancamento tipoLancamento, StatusLancamento statusLancamento) {

        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataCompetencia = dataCompetencia;
        this.dataVencimento = dataVencimento;
        this.meioPagamento = meioPagamento;
        this.valorBaixado = valorBaixado;
        this.usuario = usuario;
        this.entidade = entidade;
        this.centroCusto = centroCusto;
        this.contaBancaria = contaBancaria;
        this.cartaoCredito = cartaoCredito;
        this.tipoLancamento = tipoLancamento;
        this.statusLancamento = statusLancamento;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCompetencia() {
        return dataCompetencia;
    }

    public void setDataCompetencia(LocalDate dataCompetencia) {
        this.dataCompetencia = dataCompetencia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public StatusLancamento getStatusLancamento() {
        return statusLancamento;
    }

    public void setStatusLancamento(StatusLancamento statusLancamento) {
        this.statusLancamento = statusLancamento;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Recebimento> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
    }

    public void addPagamento(Pagamento p) {
        if(pagamentos == null) return;
        pagamentos.add(p);
        p.setLancamento(this);
    }

    public void removePagamento(Pagamento p) {
        if(pagamentos == null) return;
        pagamentos.remove(p);
        if(p.getLancamento() == this) p.setLancamento(null);
    }

    public void addRecebimento(Recebimento r) {
        if(recebimentos == null) return;
        recebimentos.add(r);
        r.setLancamento(this);
    }

    public void removeRecebimento(Recebimento r) {
        if(recebimentos == null) return;
        recebimentos.remove(r);
        if(r.getLancamento() == this) r.setLancamento(null);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lancamento that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
