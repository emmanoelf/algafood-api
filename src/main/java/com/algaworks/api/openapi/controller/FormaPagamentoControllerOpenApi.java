package com.algaworks.api.openapi.controller;

import com.algaworks.api.exceptionhandler.Problem;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.api.model.input.FormaPagamentoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Forma de pagamento inválida", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoDTO> buscar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long id,

            ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
    })
    FormaPagamentoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento",
                    required = true)
            FormaPagamentoInput formaPagamentoInput
    );

    @ApiOperation("Altera uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoDTO alterar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long id,

            @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
            FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Remove uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento removida"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long id);
}
