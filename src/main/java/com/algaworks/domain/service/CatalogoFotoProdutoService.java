package com.algaworks.domain.service;

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

        Optional<FotoProduto> fotoExistente = this.produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()){
            this.produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(novoNomeArquivo);
        produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        this.fotoStorageService.armazenar(novaFoto);

        return foto;
    }
}
