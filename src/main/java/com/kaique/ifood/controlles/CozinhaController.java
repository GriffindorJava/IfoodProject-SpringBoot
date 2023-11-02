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

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.services.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService service;

	@GetMapping
	public List<Cozinha> listar() {
		return service.listar();
	}

	/*
	 * esse método foi criado com o intuído de praticar o exercício do curso , mais
	 * não a a necessidade de usar o "produces" a não ser que você queira que a api
	 * retorne um formato especifico no endpoint
	 * 
	 * @GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) public
	 * ResponseEntity<List<Cozinha>> listar() { return
	 * ResponseEntity.status(HttpStatus.OK).body(service.listar()); }
	 */

	@GetMapping("/{id}")
	public Cozinha buscaPorId(@PathVariable Long id) {
		return service.buscaPorId(id);
	}

	@GetMapping("/buscarPorNome/{nome}")
	public List<Cozinha> buscarPorNome(String nome) {
		return service.buscarPorNome(nome);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cozinha adiciona(@RequestBody Cozinha cozinha) {
		return service.adiciona(cozinha);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{cozinhaId}")
	public Cozinha atualiza(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		return service.atualiza(cozinhaId, cozinha);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
}
