package com.kaique.ifood.controlles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)//A anotação cria e inicializa o nosso ambiente de testes.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//A anotação permite modificar o ciclo de vida da Classe de testes.
public class CozinhaControllerTestsAPI {

	@LocalServerPort
	private int port;
	
	@BeforeAll
	public void setUp() {
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	@Test
	@DisplayName("testa se o código http que retorna é o 200")
	public void deveRetornaStatus200QuandoConsultaCozinha() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("testa se retorna 201 quando cria um novo OBJ")
	public void testeRetornaStatus201QaundoCadastraCozinha() {
		RestAssured.given()
		    .body(" { \"nome\" : \" chinesa\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	@DisplayName("testa se retorna 200 quando buscar cozinha por id")
	public void testeRetornaStatus200QaundoBuscarCozinhaPorId() {
		RestAssured.given()
		    .pathParam("id", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
}
