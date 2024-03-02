package com.algaworks.api.openapi.controller;

import com.algaworks.api.exceptionhandler.Problem;
import com.algaworks.api.model.GrupoDTO;
import com.algaworks.api.model.input.GrupoInput;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
    @ApiOperation("Lista os grupos")
    List<GrupoDTO> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo é inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoDTO buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long id);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado")
    })
    GrupoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
            GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoDTO alterar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long id,

            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoInput grupoInput);

    @ApiOperation("Deleta um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo deletado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long id);
}
