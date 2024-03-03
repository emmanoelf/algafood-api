package com.algaworks.api.controller;

import com.algaworks.api.assembler.EstadoDTOAssembler;
import com.algaworks.api.assembler.EstadoInputDisassembler;
import com.algaworks.api.model.EstadoDTO;
import com.algaworks.api.model.input.EstadoInput;
import com.algaworks.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.domain.model.Estado;
import com.algaworks.domain.repository.EstadoRepository;
import com.algaworks.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoDTO> listar(){
        List<Estado> estados = estadoRepository.findAll();
        return estadoDTOAssembler.toCollectionDTO(estados);
    }

    @GetMapping("/{id}")
    public EstadoDTO buscar(@PathVariable Long id){
        Estado estado = cadastroEstadoService.buscarOuFalhar(id);
        return estadoDTOAssembler.toDTO(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput){
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        cadastroEstadoService.salvar(estado);
        return estadoDTOAssembler.toDTO(estado);
    }

    @PutMapping("/{id}")
    public EstadoDTO alterar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput){
        Estado getEstado = cadastroEstadoService.buscarOuFalhar(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, getEstado);
        getEstado = cadastroEstadoService.salvar(getEstado);
        return estadoDTOAssembler.toDTO(getEstado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long id){
        cadastroEstadoService.excluir(id);
    }
}
