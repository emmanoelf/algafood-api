package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Japonesa")
    private String nome;
}
