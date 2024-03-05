package com.algaworks.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {
    private Long id;
    private String nome;
}
