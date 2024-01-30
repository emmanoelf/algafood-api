package com.algaworks.infrastructure.service.storage;

import com.algaworks.core.storage.StorageProperties;
import com.algaworks.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try{
            String caminhoArquivo = this.getCaminhoArquivo(novaFoto.getNomeArquivo());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            this.amazonS3.putObject(putObjectRequest);
        }catch (Exception e){
               throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try{
            String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    this.storageProperties.getS3().getBucket(), caminhoArquivo
            );

            this.amazonS3.deleteObject(deleteObjectRequest);
        }catch (Exception e){
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);

        URL url = this.amazonS3.getUrl(this.storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder().url(url.toString()).build();
    }

    private String getCaminhoArquivo(String nomeArquivo){
        return String.format("%s/%s", this.storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
