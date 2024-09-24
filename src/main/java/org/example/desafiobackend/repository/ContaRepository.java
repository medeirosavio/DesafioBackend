package org.example.desafiobackend.repository;

import org.example.desafiobackend.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT c FROM Conta c WHERE (:dataVencimento IS NULL OR c.dataVencimento = :dataVencimento) " +
            "AND (:descricao IS NULL OR c.descricao LIKE %:descricao%)")
    List<Conta> findByDataVencimentoAndDescricao(@Param("dataVencimento") LocalDate dataVencimento,
                                                 @Param("descricao") String descricao);

}
