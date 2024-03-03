package com.algaworks.api.controller;

import com.algaworks.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.api.model.FotoProdutoDTO;
import com.algaworks.api.model.input.FotoProdutoInput;
import com.algaworks.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.domain.model.FotoProduto;
import com.algaworks.domain.model.Produto;
import com.algaworks.domain.service.CadastroProdutoService;
import com.algaworks.domain.service.CatalogoFotoProdutoService;
import com.algaworks.domain.service.FotoStorageService;
import com.algaworks.domain.service.FotoStorageService.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {
    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                        @Valid  FotoProdutoInput fotoProdutoInput,
                                        @RequestPart(required = true) MultipartFile arquivo) throws IOException {
        Produto produto = this.cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

        return this.fotoProdutoDTOAssembler.toDTO(fotoSalva);
    }

    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        FotoProduto fotoProduto = this.catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return this.fotoProdutoDTOAssembler.toDTO(fotoProduto);
    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try{
            FotoProduto fotoProduto = this.catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
            this.verificarCompatibilidade(mediaTypeFoto, mediaTypesAceitas);

            FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if(fotoRecuperada.temUrl()){
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            }else{
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        this.catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }

    private void verificarCompatibilidade(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypesAceita -> mediaTypesAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}
