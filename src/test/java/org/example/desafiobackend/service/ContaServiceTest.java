package org.example.desafiobackend.service;

import org.example.desafiobackend.domain.model.Conta;
import org.example.desafiobackend.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        conta = new Conta();
        conta.setId(1L);
        conta.setDataVencimento(LocalDate.now());
        conta.setDescricao("Teste");
        conta.setSituacao("Pendente");
        conta.setValor(new BigDecimal("100.0"));
    }

    @Test
    public void deveCadastrarContaComSucesso() {
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta novaConta = contaService.cadastrarConta(conta);

        assertNotNull(novaConta);
        assertEquals(1L, novaConta.getId());
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    public void deveAtualizarContaComSucesso() {
        Conta contaAtualizada = new Conta();
        contaAtualizada.setDataVencimento(LocalDate.now().plusDays(5));
        contaAtualizada.setValor(new BigDecimal("200.0"));
        contaAtualizada.setDescricao("Conta Atualizada");

        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(contaAtualizada);

        Conta contaAtualizadaRetorno = contaService.atualizarConta(1L, contaAtualizada);

        assertNotNull(contaAtualizadaRetorno);
        assertEquals(new BigDecimal("200.0"), conta.getValor());
        verify(contaRepository, times(1)).findById(1L);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    public void deveLancarExcecaoQuandoContaNaoEncontradaParaAtualizacao() {
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contaService.atualizarConta(1L, conta);
        });

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepository, times(1)).findById(1L);
    }

    @Test
    public void deveAlterarSituacaoComSucesso() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta contaAlterada = contaService.alterarSituacao(1L, "Pago");

        assertEquals("Pago", contaAlterada.getSituacao());
        verify(contaRepository, times(1)).findById(1L);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    public void deveObterContasFiltradasComSucesso() {
        LocalDate dataVencimento = LocalDate.now();
        String descricao = "Teste";

        when(contaRepository.findByDataVencimentoAndDescricao(dataVencimento, descricao)).thenReturn(List.of(conta));

        List<Conta> contasFiltradas = contaService.obterContasFiltradas(dataVencimento, descricao);

        assertEquals(1, contasFiltradas.size());
        verify(contaRepository, times(1)).findByDataVencimentoAndDescricao(dataVencimento, descricao);
    }

    @Test
    public void deveEncontrarContaPorId() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        Optional<Conta> contaEncontrada = contaService.encontrarContaPorId(1L);

        assertTrue(contaEncontrada.isPresent());
        assertEquals(1L, contaEncontrada.get().getId());
        verify(contaRepository, times(1)).findById(1L);
    }

    @Test
    public void deveObterTotalPagoPorPeriodoComSucesso() {
        LocalDate dataInicio = LocalDate.now().minusDays(10);
        LocalDate dataFim = LocalDate.now();

        when(contaRepository.obterTotalPagoPorPeriodo(dataInicio, dataFim)).thenReturn(1000.0);

        Double totalPago = contaService.obterTotalPagoPorPeriodo(dataInicio, dataFim);

        assertEquals(1000.0, totalPago);
        verify(contaRepository, times(1)).obterTotalPagoPorPeriodo(dataInicio, dataFim);
    }
}
