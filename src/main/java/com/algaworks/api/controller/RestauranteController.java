package com.algaworks.api.controller;

import com.algaworks.api.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.api.assembler.RestauranteDTOAssembler;
import com.algaworks.api.assembler.RestauranteInputDisassembler;
import com.algaworks.api.assembler.RestauranteResumoDTOAssembler;
import com.algaworks.api.model.RestauranteBasicoModel;
import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.api.model.RestauranteResumoDTO;
import com.algaworks.api.model.input.RestauranteInput;
import com.algaworks.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.RestauranteRepository;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteResumoDTOAssembler restauranteResumoDTOAssembler;

    //@JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar(){
        return restauranteBasicoModelAssembler.toCollectionModel(this.restauranteRepository.findAll());
    }

    //@JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteResumoDTO> listarApenasNomes(){
        return restauranteResumoDTOAssembler.toCollectionModel(this.restauranteRepository.findAll());
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

        return restauranteDTOAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteDTOAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput){
            try{
                Restaurante getRestaurante = cadastroRestauranteService.buscarOuFalhar(id);
                restauranteInputDisassembler.copyToDomainObject(restauranteInput, getRestaurante);
                return restauranteDTOAssembler.toModel(cadastroRestauranteService.salvar(getRestaurante));
            }catch (RestauranteNaoEncontradoException | CidadeNaoEncontradaException e){
                throw new NegocioException(e.getMessage());
            }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id){
        cadastroRestauranteService.ativar(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id){
        cadastroRestauranteService.inativar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        this.cadastroRestauranteService.abrir(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        this.cadastroRestauranteService.fechar(id);

        return ResponseEntity.noContent().build();
    }

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
