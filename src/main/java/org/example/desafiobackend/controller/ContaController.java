package org.example.desafiobackend.controller;

import org.example.desafiobackend.domain.model.Conta;
import org.example.desafiobackend.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.cadastrarConta(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta contaAtualizada) {
        Conta conta = contaService.atualizarConta(id, contaAtualizada);
        return ResponseEntity.ok(conta);
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<Conta> alterarSituacao(@PathVariable Long id, @RequestBody String novaSituacao) {
        Conta conta = contaService.alterarSituacao(id, novaSituacao);
        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> obterContasFiltradas(
            @RequestParam(required = false) LocalDate dataVencimento,
            @RequestParam(required = false) String descricao) {
        List<Conta> contas = contaService.obterContasFiltradas(dataVencimento, descricao);
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}/buscar")
    public ResponseEntity<Conta> encontrarContaPorId(@PathVariable Long id) {
        return contaService.encontrarContaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


