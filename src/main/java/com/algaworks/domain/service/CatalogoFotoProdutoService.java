package com.algaworks.domain.service;

import com.algaworks.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.domain.model.FotoProduto;
import com.algaworks.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream inputStream){
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String novoNomeArquivo = this.fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;


        Optional<FotoProduto> fotoExistente = this.produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()){
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            this.produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(novoNomeArquivo);
        produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .inputStream(inputStream)
                .build();

        this.fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId){
        return this.produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() ->
                new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId){
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);

        this.produtoRepository.delete(fotoProduto);
        this.produtoRepository.flush();

        this.fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }
}
