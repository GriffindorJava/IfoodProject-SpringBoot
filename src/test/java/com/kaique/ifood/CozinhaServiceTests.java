package com.kaique.ifood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.services.CozinhaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CozinhaServiceTests {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	@DisplayName("deve cadastrar uma cozinha com sucesso")
	public void testeCadastroCozinha() {
		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("novaCozinha");

		// ação
		novaCozinha = cozinhaService.adiciona(novaCozinha);

		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	@DisplayName("testar erro se nome for null")
	public void testeCadastroCozinhaSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		assertThrows(ConstraintViolationException.class,() ->  {
			cozinhaService.adiciona(novaCozinha);
		});
		
	}
}
