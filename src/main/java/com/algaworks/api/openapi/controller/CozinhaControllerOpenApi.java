package com.algaworks.api.openapi.controller;

import com.algaworks.api.exceptionhandler.Problem;
import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    Page<CozinhaDTO> listar(Pageable pageable);

    @ApiOperation("Busca cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaDTO buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses(
            @ApiResponse(code = 201, message = "Cozinha cadatrada")
    )
    CozinhaDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
            CozinhaInput cozinhaInput
    );

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaDTO atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados")
            CozinhaInput cozinhaInput
    );

    @ApiOperation("Remove uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha removida"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
            @ApiResponse(code = 409, message = "Cozinha está em uso", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long id);
}
