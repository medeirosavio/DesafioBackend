package org.example.desafiobackend.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.desafiobackend.domain.model.Conta;
import org.example.desafiobackend.dto.ContaImportDTO;
import org.example.desafiobackend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class ContaImportService {

    @Autowired
    private ContaRepository contaRepository;

    public void importContas(MultipartFile file) throws Exception {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<ContaImportDTO> csvToBean = new CsvToBeanBuilder<ContaImportDTO>(reader)
                    .withType(ContaImportDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ContaImportDTO> contas = csvToBean.parse();

            for (ContaImportDTO contaDTO : contas) {
                Conta conta = new Conta();
                conta.setDataVencimento(contaDTO.getDataVencimento());
                conta.setDataPagamento(contaDTO.getDataPagamento());
                conta.setValor(contaDTO.getValor());
                conta.setDescricao(contaDTO.getDescricao());
                conta.setSituacao(contaDTO.getSituacao());
                contaRepository.save(conta);
            }
        }
    }
}
