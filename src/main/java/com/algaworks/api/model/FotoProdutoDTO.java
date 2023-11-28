package com.algaworks.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
