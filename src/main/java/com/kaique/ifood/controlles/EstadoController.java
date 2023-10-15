package com.kaique.ifood.controlles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.services.EstadoService;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService service;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());
	}

	/*
	 * implementação temporária
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscaPorId(@PathVariable Long id) {
		if (service.buscaPorId(id).isPresent())
			return ResponseEntity.ok(service.buscaPorId(id).get());

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Estado> adiciona(@RequestBody Estado estado) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.adiciona(estado));
		} catch (ConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{EstadiId}")
	public ResponseEntity<Estado> adiciona(@PathVariable Long EstadiId, @RequestBody Estado estado) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.atualiza(EstadiId, estado));
		} catch (ConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {

			/* tratamento temporario */
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
