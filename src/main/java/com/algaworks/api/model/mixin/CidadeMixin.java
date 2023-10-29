package com.algaworks.api.model.mixin;

import com.algaworks.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
