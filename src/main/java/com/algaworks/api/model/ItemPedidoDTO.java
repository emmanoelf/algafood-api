package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {
    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Feijãozinho de todos os sabores")
    private String produtoNome;

    @ApiModelProperty(example = "2")
    private Integer quantidade;

    @ApiModelProperty(example = "78.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "157.80")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;
}
