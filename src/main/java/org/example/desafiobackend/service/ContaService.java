package org.example.desafiobackend.service;

import org.example.desafiobackend.domain.model.Conta;
import org.example.desafiobackend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta atualizarConta(Long id, Conta contaAtualizada) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            Conta contaExistente = contaOptional.get();
            contaExistente.setDataVencimento(contaAtualizada.getDataVencimento());
            contaExistente.setDataPagamento(contaAtualizada.getDataPagamento());
            contaExistente.setValor(contaAtualizada.getValor());
            contaExistente.setDescricao(contaAtualizada.getDescricao());
            contaExistente.setSituacao(contaAtualizada.getSituacao());
            return contaRepository.save(contaExistente);
        } else {
            throw new RuntimeException("Conta não encontrada");
        }
    }

    public Conta alterarSituacao(Long id, String novaSituacao) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            Conta contaExistente = contaOptional.get();
            contaExistente.setSituacao(novaSituacao);
            return contaRepository.save(contaExistente);
        } else {
            throw new RuntimeException("Conta não encontrada");
        }
    }

    public List<Conta> obterContasFiltradas(LocalDate dataVencimento, String descricao) {
        return contaRepository.findByDataVencimentoAndDescricao(dataVencimento, descricao);
    }

    public Optional<Conta> encontrarContaPorId(Long id) {
        return contaRepository.findById(id);
    }

    public Double obterTotalPagoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return contaRepository.obterTotalPagoPorPeriodo(dataInicio, dataFim);
    }

}
