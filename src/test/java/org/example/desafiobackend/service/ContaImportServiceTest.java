package org.example.desafiobackend.service;

import org.example.desafiobackend.domain.model.Conta;
import org.example.desafiobackend.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ContaImportServiceTest {

    @Mock
    private ContaRepository contaRepository;
    @InjectMocks
    private ContaImportService contaImportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportContas() throws Exception {
        // Dados do CSV simulado
        String csvData = "dataVencimento,dataPagamento,valor,descricao,situacao\n"
                + "2024-01-01,2024-01-05,1000.00,Conta de luz,PAGA\n"
                + "2024-02-01,2024-02-03,2000.00,Conta de água,PENDENTE\n";

        // Cria um Mock do MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "contas.csv", "text/csv", csvData.getBytes());

        // Executa o método a ser testado
        contaImportService.importContas(file);

        // Verifica se o repository.save() foi chamado com os dados corretos
        verify(contaRepository, times(2)).save(any(Conta.class));

        // Valida os dados salvos usando ArgumentCaptor (opcional, se quiser checar valores específicos)
        Conta expectedConta1 = new Conta();
        expectedConta1.setDataVencimento(LocalDate.of(2024, 1, 1));
        expectedConta1.setDataPagamento(LocalDate.of(2024, 1, 5));
        expectedConta1.setValor(new BigDecimal("1000.00"));
        expectedConta1.setDescricao("Conta de luz");
        expectedConta1.setSituacao("PAGA");

        Conta expectedConta2 = new Conta();
        expectedConta2.setDataVencimento(LocalDate.of(2024, 2, 1));
        expectedConta2.setDataPagamento(LocalDate.of(2024, 2, 3));
        expectedConta2.setValor(new BigDecimal("2000.00"));
        expectedConta2.setDescricao("Conta de água");
        expectedConta2.setSituacao("PENDENTE");

        // Captura os argumentos usados no save
        verify(contaRepository).save(argThat(conta ->
                conta.getDataVencimento().equals(LocalDate.of(2024, 1, 1)) &&
                        conta.getValor().equals(new BigDecimal("1000.00")) &&
                        conta.getDescricao().equals("Conta de luz") &&
                        conta.getSituacao().equals("PAGA")
        ));

        verify(contaRepository).save(argThat(conta ->
                conta.getDataVencimento().equals(LocalDate.of(2024, 2, 1)) &&
                        conta.getValor().equals(new BigDecimal("2000.00")) &&
                        conta.getDescricao().equals("Conta de água") &&
                        conta.getSituacao().equals("PENDENTE")
        ));
    }
}
