package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {

    @ApiModelProperty(example = "2a025fb7-3f51-4f8c-93e7-fc3b962b93b6_Butcher.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Butcher")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "13942")
    private Long tamanho;
}
