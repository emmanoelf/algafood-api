package com.algaworks.api.controller;

import com.algaworks.api.assembler.CidadeDTOAssembler;
import com.algaworks.api.assembler.CidadeInputDisassembler;
import com.algaworks.api.exceptionhandler.Problem;
import com.algaworks.api.model.CidadeDTO;
import com.algaworks.api.model.input.CidadeInput;
import com.algaworks.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.repository.CidadeRepository;
import com.algaworks.domain.service.CadastroCidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDTO> listar(){
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeDTOAssembler.toCollectionDTO(cidades);
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{id}")
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id){
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(id);
        return cidadeDTOAssembler.toDTO(cidade);
    }

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade criada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cadastroCidadeService.salvar(cidade);
            return cidadeDTOAssembler.toDTO(cidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{id}")
    public CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            @RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade getCidade = cadastroCidadeService.buscarOuFalhar(id);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, getCidade);
            getCidade = cadastroCidadeService.salvar(getCidade);
            return cidadeDTOAssembler.toDTO(getCidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Deleta uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id){
        cadastroCidadeService.excluir(id);
    }

}
