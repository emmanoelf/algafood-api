package com.algaworks.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name="produto_id")
    private Long id;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;
}
