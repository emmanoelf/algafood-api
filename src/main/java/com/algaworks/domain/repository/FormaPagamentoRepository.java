package com.algaworks.domain.repository;

import com.algaworks.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository  extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getDataUltimaAtualizacao();

    @Query("select max(dataAtualizacao) from FormaPagamento where id = :id")
    OffsetDateTime getDataUltimaAtualizacaoById(Long id);

}
