package com.projetofef.domains;

import com.projetofef.domains.enums.TipoLancamento;
import com.projetofef.infra.TipoLancamentoConverter;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.infra.StatusLancamentoConverter;
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
    @Column(nullable = false, length = 120)
    private String tipo;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String descricao;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataCompetencia = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataVencimento = LocalDate.now();

    @NotBlank
    @Column(nullable = false, length = 120)
    private String meioPagamento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal valorBaixado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idContaBancaria", nullable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

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
    @JoinColumn(name = "idCartaoCredito", nullable = false)
    @JsonBackReference
    private CartaoCredito cartaoCredito;

    @Convert(converter = TipoLancamentoConverter.class)
    @Column(name = "tipoLancamento", nullable = false)
    private TipoLancamento tipoLancamento;

    @Convert(converter = StatusLancamentoConverter.class)
    @Column(name = "statusLancamento", nullable = false)
    private StatusLancamento statusLancamento;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "lancamento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("observacao ASC")
    private List<Recebimento> recebimentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "lancamento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("observacao ASC")
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Lancamento() {
        this.valor = BigDecimal.ZERO;
        this.valorBaixado = BigDecimal.ZERO;
    }

    public Lancamento(Integer id, String tipo, String descricao, BigDecimal valor, LocalDate dataCompetencia, LocalDate dataVencimento, String meioPagamento, BigDecimal valorBaixado, ContaBancaria contaBancaria, Usuario usuario, Entidade entidade, CentroCusto centroCusto, CartaoCredito cartaoCredito, TipoLancamento tipoLancamento, StatusLancamento statusLancamento) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor != null ? valor : BigDecimal.ZERO;
        this.dataCompetencia = dataCompetencia;
        this.dataVencimento = dataVencimento;
        this.meioPagamento = meioPagamento;
        this.valorBaixado = valorBaixado != null ? valorBaixado : BigDecimal.ZERO;
        this.contaBancaria = contaBancaria;
        this.usuario = usuario;
        this.entidade = entidade;
        this.centroCusto = centroCusto;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(String meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
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

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
    }

    public void addRecebimento(Recebimento re) {
        if(recebimentos == null) return;
        recebimentos.add(re);
        re.setLancamento(this);
    }

    public void removeRecebimento(Recebimento re) {
        if(recebimentos == null) return;
        recebimentos.remove(re);
        if(re.getLancamento() == this) re.setLancamento(null);
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addPagamento(Pagamento pa) {
        if(pagamentos == null) return;
        pagamentos.add(pa);
        pa.setLancamento(this);
    }

    public void removePagamento(Pagamento pa) {
        if(pagamentos == null) return;
        pagamentos.remove(pa);
        if(pa.getLancamento() == this) pa.setLancamento(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}