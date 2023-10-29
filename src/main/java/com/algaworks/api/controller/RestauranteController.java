package com.algaworks.api.controller;

import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.core.validation.ValidacaoException;
import com.algaworks.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.RestauranteRepository;
import com.algaworks.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteDTO> listar(){
        return toColletionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        return toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid Restaurante restaurante) {
        try{
            return toDTO(cadastroRestauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante){
            try{
                Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);
                BeanUtils.copyProperties(restaurante, getRestaurante, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
                return toDTO(cadastroRestauranteService.salvar(getRestaurante));
            }catch (RestauranteNaoEncontradoException e){
                throw new NegocioException(e.getMessage());
            }
    }

    @PatchMapping("/{id}")
    public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
                                        HttpServletRequest request){
        Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);

        merge(campos, getRestaurante, request);
        validate(getRestaurante, "restaurante");

        return atualizar(id, getRestaurante);
    }

    private void validate(Restaurante restaurante, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if(bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
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

    private RestauranteDTO toDTO(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);

        return restauranteDTO;
    }

    private List<RestauranteDTO> toColletionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }
}
