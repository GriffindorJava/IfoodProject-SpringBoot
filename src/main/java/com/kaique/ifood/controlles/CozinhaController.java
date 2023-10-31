package com.kaique.ifood.controlles;

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
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.services.CozinhaService;

import jakarta.validation.ConstraintViolationException;

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
	 * esse método foi criado com o intuído de praticar o exercício do curso , mais
	 * não a a necessidade de usar o "produces" a não ser que você queira que a api
	 * retorne um formato especifico no endpoint
	 * 
	 * @GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) public
	 * ResponseEntity<List<Cozinha>> listar() { return
	 * ResponseEntity.status(HttpStatus.OK).body(service.listar()); }
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscaPorId(@PathVariable Long id) {
		if (service.buscaPorId(id).isPresent())
			return ResponseEntity.ok(service.buscaPorId(id).get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/buscarPorNome/{nome}")
	public ResponseEntity<List<Cozinha>> buscarPorNome(@RequestParam String nome) {
		return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNome(nome));
	}

	@PostMapping
	public ResponseEntity<Cozinha> adiciona(@RequestBody Cozinha cozinha) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.adiciona(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualiza(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.atualiza(cozinhaId, cozinha));
		} catch (ConstraintViolationException | EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			service.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} //catch (EntidadeNaoEncontradaException e) {
			//return ResponseEntity.notFound().build();
		//}
	}
}
