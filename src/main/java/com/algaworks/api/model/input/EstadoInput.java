package com.algaworks.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInput {

    @ApiModelProperty(example = "Rio Grande do Sul", required = true)
    @NotBlank
    private String nome;
}
