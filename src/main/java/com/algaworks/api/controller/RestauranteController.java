package com.algaworks.api.controller;

import com.algaworks.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.RestauranteRepository;
import com.algaworks.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){return restauranteRepository.findAll();}

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {return cadastroRestauranteService.buscarOuFalhar(id);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try{
            return cadastroRestauranteService.salvar(restaurante);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
            try{
                Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);
                BeanUtils.copyProperties(restaurante, getRestaurante, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
                return cadastroRestauranteService.salvar(getRestaurante);
            }catch (RestauranteNaoEncontradoException e){
                throw new NegocioException(e.getMessage());
            }
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
                                        HttpServletRequest request){
        Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);

        merge(campos, getRestaurante, request);

        return atualizar(id, getRestaurante);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request){
        ServletServerHttpRequest servletServer = new ServletServerHttpRequest(request);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServer);
        }

    }
}
