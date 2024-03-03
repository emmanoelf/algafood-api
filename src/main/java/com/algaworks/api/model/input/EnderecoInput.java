package com.algaworks.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(example = "12345-666", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Randomicamente randomica", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "\"666\"", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Apto 69")
    private String complemento;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
