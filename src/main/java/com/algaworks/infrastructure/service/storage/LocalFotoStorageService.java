package com.algaworks.infrastructure.service.storage;

import com.algaworks.core.storage.StorageProperties;
import com.algaworks.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try{
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        }catch (Exception e){
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try{
            Path arquivoPath = this.getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        }catch (Exception e){
            throw new StorageException("Não foi possível remover o arquivo", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try{
            Path arquivoPath = this.getArquivoPath(nomeArquivo);

            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath))
                    .build();
        }catch (Exception e){
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo){
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
