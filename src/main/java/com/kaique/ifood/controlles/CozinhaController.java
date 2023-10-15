package com.kaique.ifood.controlles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.services.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService service;

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());
	}

	/*
	 * esse método foi criado com o intuído de praticar o exercício do curso , mais não
	 * a a necessidade de usar o "produces" a não ser que você queira que a api
	 * retorne um formato especifico no endpoint
	 * 
	 * @GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
	 * public ResponseEntity<List<Cozinha>> listar() { return
	 * ResponseEntity.status(HttpStatus.OK).body(service.listar()); }
	 */
}
