package com.algaworks.api.controller;

import com.algaworks.api.assembler.RestauranteDTOAssembler;
import com.algaworks.api.assembler.RestauranteInputDisassembler;
import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.api.model.input.RestauranteInput;
import com.algaworks.api.model.view.RestauranteView;
import com.algaworks.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.RestauranteRepository;
import com.algaworks.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
//import java.lang.reflect.Field;
import java.util.List;
//import java.util.Map;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;


    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteDTO> listar(){
        return restauranteDTOAssembler.toColletionDTO(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteDTO> listarApenasNomes(){
        return listar();
    }

    /*@GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String projecao){
        List<Restaurante> restaurantes = this.restauranteRepository.findAll();
        List<RestauranteDTO> restaurantesDTOs = restauranteDTOAssembler.toColletionDTO(restaurantes);

        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDTOs);
        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);

        if("apenas-nome".equals(projecao)){
            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        }else if("completo".equals(projecao)){
            restaurantesWrapper.setSerializationView(null);
        }

        return restaurantesWrapper;
    }*/

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        return restauranteDTOAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteDTOAssembler.toDTO(cadastroRestauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput){
            try{
                Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);
                restauranteInputDisassembler.copyToDomainObject(restauranteInput, getRestaurante);
                return restauranteDTOAssembler.toDTO(cadastroRestauranteService.salvar(getRestaurante));
            }catch (RestauranteNaoEncontradoException | CidadeNaoEncontradaException e){
                throw new NegocioException(e.getMessage());
            }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id){
        cadastroRestauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id){
        cadastroRestauranteService.inativar(id);
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id) { this.cadastroRestauranteService.abrir(id); }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) { this.cadastroRestauranteService.fechar(id); }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try{
            this.cadastroRestauranteService.ativar(restaurantesIds);
        }catch(RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try{
            this.cadastroRestauranteService.inativar(restaurantesIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    /*@PatchMapping("/{id}")
    public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
                                        HttpServletRequest request){
        Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);

        merge(campos, getRestaurante, request);
        validate(getRestaurante, "restaurante");

        return atualizar(id, getRestaurante);
    }
     */

    /*private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request){
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
    }*/
}
