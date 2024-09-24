package org.example.desafiobackend.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaImportDTO {

    @CsvBindByName(column = "dataVencimento")
    @CsvDate("yyyy-MM-dd")
    private LocalDate dataVencimento;
    @CsvBindByName(column = "dataPagamento")
    @CsvDate("yyyy-MM-dd")
    private LocalDate dataPagamento;
    @CsvBindByName(column = "valor")
    private BigDecimal valor;
    @CsvBindByName(column = "descricao")
    private String descricao;
    @CsvBindByName(column = "situacao")
    private String situacao;
}

