package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EnderecoDTO extends RepresentationModel<EnderecoDTO> {

    @ApiModelProperty(example = "12345-666")
    private String cep;

    @ApiModelProperty(example = "Rua Randomicamente Randomica")
    private String logradouro;

    @ApiModelProperty(example = "\"666\"")
    private String numero;

    @ApiModelProperty(example = "7")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;

    private CidadeResumoDTO cidade;
}
