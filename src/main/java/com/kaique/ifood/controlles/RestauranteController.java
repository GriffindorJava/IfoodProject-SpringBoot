package com.kaique.ifood.controlles;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.services.RestauranteService;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService service;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscaPorId(@PathVariable Long id) {
		if (service.buscaPorId(id).isPresent())
			return ResponseEntity.ok(service.buscaPorId(id).get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/filtroTaxa/por-taxa-frete")
	public ResponseEntity<List<Restaurante>> filtraPorTaxas(BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return ResponseEntity.ok().body(service.filtraPorTaxas(taxaInicial, taxaFinal));
	}

	/*
	@GetMapping("/filtra/nome-e-id")
	public ResponseEntity<List<Restaurante>> buscaPorNomeEIdDeCozinha(String nome, BigDecimal id) {
		return ResponseEntity.ok().body(service.buscaPorNomeEIdDeCozinha(nome, id));
	}*/

	@GetMapping("/filtra/por-nome-e-frete")
	public ResponseEntity<List<Restaurante>> buscaRTTPorNomeFrete(String nome, @RequestParam BigDecimal taxaFreteInicia,
			BigDecimal taxaFreteFinal) {
		return ResponseEntity.ok().body(service.buscaRTTPorNomeFrete(nome, taxaFreteInicia, taxaFreteFinal));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("filtra/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return service.restaurantesComFreteGratis(nome);
	}

	@PostMapping
	public ResponseEntity<?> adiciona(@RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.adiciona(restaurante));
		} catch (ConstraintViolationException | NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> atualiza(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.atualiza(restauranteId, restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		try {
			service.deletar(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}
}
