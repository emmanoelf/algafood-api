package com.algaworks.api.openapi.controller;

import com.algaworks.api.model.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista de permissões")
    CollectionModel<PermissaoDTO> listar();
}
