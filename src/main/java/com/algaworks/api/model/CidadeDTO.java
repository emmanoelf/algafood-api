package com.algaworks.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {
    private Long id;
    private String nome;
    private EstadoDTO estado;
}
