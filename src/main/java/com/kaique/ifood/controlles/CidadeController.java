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

import com.kaique.ifood.entities.Cidade;
import com.kaique.ifood.services.CidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService service;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Cidade> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public Cidade buscaPorId(@PathVariable Long id) {
		return service.buscaPorId(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cidade adiciona(@Valid @RequestBody Cidade cidade) {
		return service.adiciona(cidade);
	}

	@PutMapping("/{cidadeId}")
	public Cidade atualiza(@PathVariable Long cidadeId, @Valid @RequestBody Cidade cidade) {
		return service.atualiza(cidadeId, cidade);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
}
