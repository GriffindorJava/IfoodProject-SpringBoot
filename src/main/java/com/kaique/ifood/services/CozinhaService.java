package com.kaique.ifood.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.CozinhaRepository;

import jakarta.transaction.Transactional;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository repository;

	public List<Cozinha> listar() {
		return repository.findAll();
	}

	public Cozinha buscaPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id)));
	}

	public List<Cozinha> buscarPorNome(String nome) {
		return repository.findByNomeContainsIgnoreCase(nome);
	}

	@Transactional
	public Cozinha adiciona(Cozinha Cozinha) {
		return repository.save(Cozinha);
	}

	@Transactional
	public Cozinha atualiza(Long id, Cozinha NovaCozinha) {
		Cozinha CozinhaAtual = buscaPorId(id);
		BeanUtils.copyProperties(NovaCozinha, CozinhaAtual, "id");
		return repository.save(CozinhaAtual);
	}

	@Transactional
	public void deletar(Long id) {
		buscaPorId(id);
		repository.deleteById(id);
	}
}