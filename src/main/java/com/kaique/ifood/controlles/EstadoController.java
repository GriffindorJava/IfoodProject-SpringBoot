package com.kaique.ifood.controlles;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.services.EstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService service;

	@GetMapping
	public List<Estado> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public Estado buscaPorId(@PathVariable Long id) {
		return service.buscaPorId(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Estado adiciona(@Valid @RequestBody Estado estado) {
		return service.adiciona(estado);
	}

	@PutMapping("/{estadiId}")
	public Estado atualiza(@PathVariable Long estadiId,@Valid @RequestBody Estado estado) {
		return service.atualiza(estadiId, estado);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
}
