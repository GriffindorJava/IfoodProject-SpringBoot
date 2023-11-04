package com.kaique.ifood.controlles;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.kaique.ifood.services.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService service;

	@GetMapping
	public List<Restaurante> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public Restaurante buscaPorId(@PathVariable Long id) {
		return service.buscaPorId(id);
	}

	@GetMapping("/filtroTaxa/por-taxa-frete")
	public List<Restaurante> filtraPorTaxas(BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal) {
		return service.filtraPorTaxas(taxaInicial, taxaFinal);
	}

	/*
	 * @GetMapping("/filtra/nome-e-id") public ResponseEntity<List<Restaurante>>
	 * buscaPorNomeEIdDeCozinha(String nome, BigDecimal id) { return
	 * ResponseEntity.ok().body(service.buscaPorNomeEIdDeCozinha(nome, id)); }
	 */

	@GetMapping("/filtra/por-nome-e-frete")
	public List<Restaurante> buscaRTTPorNomeFrete(String nome, @RequestParam BigDecimal taxaFreteInicia,
			BigDecimal taxaFreteFinal) {
		return service.buscaRTTPorNomeFrete(nome, taxaFreteInicia, taxaFreteFinal);
	}

	@GetMapping("/filtra/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return service.restaurantesComFreteGratis(nome);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Restaurante adiciona(@Valid @RequestBody Restaurante restaurante) {
		return service.adiciona(restaurante);
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualiza(@PathVariable Long restauranteId,@Valid @RequestBody Restaurante restaurante) {
		return service.atualiza(restauranteId, restaurante);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
}
