package com.algaworks.api.controller;

import com.algaworks.api.assembler.ProdutoDTOAssembler;
import com.algaworks.api.assembler.ProdutoInputDisassembler;
import com.algaworks.api.model.ProdutoDTO;
import com.algaworks.api.model.input.ProdutoInput;
import com.algaworks.domain.model.Produto;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.ProdutoRepository;
import com.algaworks.domain.service.CadastroProdutoService;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoDTOAssembler produtoDTOAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
                                   @RequestParam(required = false) boolean incluirInativos){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos = null;

        if(incluirInativos){
            produtos = this.produtoRepository.findTodosByRestaurante(restaurante);
        } else{
            produtos =this.produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return this.produtoDTOAssembler.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        Produto produto = this.cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return this.produtoDTOAssembler.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = this.produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = this.cadastroProdutoService.save(produto);
        return this.produtoDTOAssembler.toDTO(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                              @RequestBody @Valid ProdutoInput produtoInput){
        Produto produto = this.cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        this.produtoInputDisassembler.copyToDomainObject(produtoInput, produto);
        produto = this.cadastroProdutoService.save(produto);
        return this.produtoDTOAssembler.toDTO(produto);
    }

}
