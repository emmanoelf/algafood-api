package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.util.DatabaseCleaner;
import com.algaworks.algafoodapi.util.ResourceUtils;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.repository.CozinhaRepository;
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

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    private int amountCozinhas;
    private Cozinha cozinha;
    private String jsonCozinhaChinesa;

    private static final int ID_COZINHA_INEXISTENTE = 666;

    @BeforeEach
    public void sistemUnderTest(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
        jsonCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
        RestAssured.given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter2Cozinhas_QuandoConsultarCozinhas(){
        RestAssured.given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", Matchers.hasSize(amountCozinhas));
    }

    @Test
    public void deveRetornar201_QuandoCadastrarCozinha(){
        RestAssured.given()
                .body(jsonCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){
        RestAssured.given()
                .pathParams("id", cozinha.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinha.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        RestAssured.given()
                .pathParams("id", ID_COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados(){
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        this.cozinha = new Cozinha();
        cozinha.setNome("Americana");
        cozinhaRepository.save(cozinha);

        amountCozinhas = (int) cozinhaRepository.count();
    }
}
