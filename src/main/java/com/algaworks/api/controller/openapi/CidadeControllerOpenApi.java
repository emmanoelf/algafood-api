package com.algaworks.api.controller.openapi;

import com.algaworks.api.exceptionhandler.Problem;
import com.algaworks.api.model.CidadeDTO;
import com.algaworks.api.model.input.CidadeInput;

import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
    @ApiOperation("Lista as cidades")
    public List<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade criada")
    })
    CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                               CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            CidadeInput cidadeInput);


    @ApiOperation("Deleta uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cidade", example = "1") Long id);
}
