package com.projetofef.services;

import com.projetofef.domains.*;
import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.StatusFatura;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.domains.enums.TipoLancamento;
import com.projetofef.domains.enums.TipoTransacao;
import com.projetofef.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DBService {
    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepo;

    @Autowired
    private CentroCustoRepository centroCustoRepo;

    @Autowired
    private ContaBancariaRepository contaBancariaRepo;

    @Autowired
    private EntidadeRepository entidadeRepo;

    @Autowired
    private FaturaCartaoRepository faturaCartaoRepo;

    @Autowired
    private LancamentoRepository lancamentoRepo;

    @Autowired
    private MovimentoContaRepository movimentoContaRepo;

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @Autowired
    private RecebimentoRepository recebimentoRepo;

    @Autowired
    private TransferenciaRepository transferenciaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private InvestimentoRepository investimentoRepo;

    @Transactional
    public void initDB(){
        Usuario usuario01 = new Usuario(null, "Eduardo", "eduardozaparoli10@yahoo.com", LocalDate.now());
        ContaBancaria contaBancaria01 = new ContaBancaria(null, "Santander", "Fernandópolis", 01, "Apelido", new BigDecimal("0"), LocalDate.now(), 'S', usuario01);
        Investimento investimento01 = new Investimento(null, "LCA", "123456", new BigDecimal("1000"), new BigDecimal("1,2"), contaBancaria01);
        CartaoCredito cartaoCredito01 = new CartaoCredito(null, "VISA", "Santander", "Apelido", LocalDate.now(), LocalDate.now(), 'S', usuario01);
        CentroCusto centroCusto01 = new CentroCusto(null, "Almoxarifado", "01", 1, usuario01);
        Entidade entidade01 = new Entidade(null, "NomeEntidade", "01", "TipoEntidade", usuario01);
        FaturaCartao faturaCartao01 = new FaturaCartao(null, LocalDate.now(), LocalDate.now(), LocalDate.now(), new BigDecimal("1000"), cartaoCredito01, StatusFatura.FECHADA);
        Lancamento lancamento01 = new Lancamento(null, "Tipo", "Descricao", new BigDecimal("1000"), LocalDate.now(), LocalDate.now(), "Cartão", new BigDecimal("1000"), contaBancaria01, usuario01, entidade01, centroCusto01, cartaoCredito01, TipoLancamento.PAGAR, StatusLancamento.PENDENTE);
        MovimentoConta movimentoConta01 = new MovimentoConta(null, LocalDate.now(), "Crédito", new BigDecimal("1000"), "Historico", contaBancaria01, investimento01, TipoTransacao.CREDITO);
        Pagamento pagamento01 = new Pagamento(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", lancamento01, contaBancaria01, MeioPagamento.PIX);
        Recebimento recebimento01 = new Recebimento(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", contaBancaria01, lancamento01);
        Transferencia transferencia01 = new Transferencia(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", contaBancaria01);

        cartaoCreditoRepo.save(cartaoCredito01);
        centroCustoRepo.save(centroCusto01);
        contaBancariaRepo.save(contaBancaria01);
        entidadeRepo.save(entidade01);
        faturaCartaoRepo.save(faturaCartao01);
        lancamentoRepo.save(lancamento01);
        movimentoContaRepo.save(movimentoConta01);
        pagamentoRepo.save(pagamento01);
        recebimentoRepo.save(recebimento01);
        transferenciaRepo.save(transferencia01);
        usuarioRepo.save(usuario01);
    }
}