package com.projetofef.services;

import com.projetofef.domains.CartaoCredito;
import com.projetofef.domains.CentroCusto;
import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Entidade;
import com.projetofef.domains.FaturaCartao;
import com.projetofef.domains.Lancamento;
import com.projetofef.domains.MovimentoConta;
import com.projetofef.domains.Pagamento;
import com.projetofef.domains.Recebimento;
import com.projetofef.domains.Transferencia;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.StatusFatura;
import com.projetofef.domains.enums.StatusLancamento;
import com.projetofef.domains.enums.TipoLancamento;
import com.projetofef.domains.enums.TipoTransacao;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.repositories.CentroCustoRepository;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.repositories.EntidadeRepository;
import com.projetofef.repositories.FaturaCartaoRepository;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.MovimentoContaRepository;
import com.projetofef.repositories.PagamentoRepository;
import com.projetofef.repositories.RecebimentoRepository;
import com.projetofef.repositories.TransferenciaRepository;
import com.projetofef.repositories.UsuarioRepository;
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

    @Transactional
    public void initDB(){
        CartaoCredito cartaoCredito01 = new CartaoCredito(null, "VISA", "Santander", "Apelido", LocalDate.now(), LocalDate.now(), 'S');
        CentroCusto centroCusto01 = new CentroCusto(null, "Almoxarifado", "01", 1, usuario01);
        ContaBancaria contaBancaria01 = new ContaBancaria(null, "Santander", "Fernandópolis", 01, "Apelido", new BigDecimal("0"), LocalDate.now(), 'S', usuario01);
        Entidade entidade01 = new Entidade(null, "NomeEntidade", "01", "TipoEntidade", usuario01);
        FaturaCartao faturaCartao01 = new FaturaCartao(null, "Competencia", LocalDate.now(), LocalDate.now(), new BigDecimal("1000"), cartaoCredito01, StatusFatura.FECHADA);
        Lancamento lancamento01 = new Lancamento(null, "Descricao", new BigDecimal("1000"), LocalDate.now(), LocalDate.now(), "Cartão", new BigDecimal("1000"), usuario01, entidade01, centroCusto01, contaBancaria01, cartaoCredito01, TipoLancamento.PAGAR, StatusLancamento.PENDENTE);
        MovimentoConta movimentoConta01 = new MovimentoConta(null, LocalDate.now(), "Crédito", new BigDecimal("1000"), "Historico", contaBancaria01, TipoTransacao.CREDITO);
        Pagamento pagamento01 = new Pagamento(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", contaBancaria01, lancamento01);
        Recebimento recebimento01 = new Recebimento(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", contaBancaria01, lancamento01);
        Transferencia transferencia01 = new Transferencia(null, LocalDate.now(), new BigDecimal("1000"), "Observacao", contaBancaria01);
        Usuario usuario01 = new Usuario(null, "Eduardo", "eduardozaparoli10@yahoo.com", LocalDate.now());

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
