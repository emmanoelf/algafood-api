package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Beco Diagonal")
    private String nome;

    @ApiModelProperty(example = "RS")
    private String estado;
}
