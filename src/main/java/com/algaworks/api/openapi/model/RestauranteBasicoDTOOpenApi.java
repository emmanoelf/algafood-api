package com.algaworks.api.openapi.model;

import com.algaworks.api.model.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoDTOOpenApi {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Mandiare")
    private String nome;

    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;
}
