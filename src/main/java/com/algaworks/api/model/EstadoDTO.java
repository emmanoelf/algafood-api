package com.algaworks.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {
    private Long id;
    private String nome;
}
