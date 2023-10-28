package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.util.DatabaseCleaner;
import com.algaworks.algafoodapi.util.ResourceUtils;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.CozinhaRepository;
import com.algaworks.domain.repository.RestauranteRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final int RESTAURANTE_ID_INEXISTENTE = 666;

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private Restaurante restaurante;
    private int amountRestaurante;

    @BeforeEach
    public void systemUnderTest(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void deveRetornarStatus200EQuantidadeDeRestaurantes_QuandoConsultarRestaurantes(){
        RestAssured.given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("", Matchers.hasSize(amountRestaurante));
    }



    private void prepareData(){
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");

        cozinhaRepository.save(cozinhaBrasileira);
        cozinhaRepository.save(cozinhaAmericana);

        this.restaurante = new Restaurante();
        this.restaurante.setNome("Burger Top");
        this.restaurante.setTaxaFrete(new BigDecimal(10));
        this.restaurante.setCozinha(cozinhaAmericana);

        Restaurante comidaMineira = new Restaurante();
        comidaMineira.setNome("Comida Mineira");
        comidaMineira.setTaxaFrete(new BigDecimal(10));
        comidaMineira.setCozinha(cozinhaBrasileira);

        restauranteRepository.save(restaurante);
        restauranteRepository.save(comidaMineira);

        this.amountRestaurante = (int) restauranteRepository.count();
    }
}
