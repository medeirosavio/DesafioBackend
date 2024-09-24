package org.example.desafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaImportDTO {
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String descricao;
    private String situacao;
}
