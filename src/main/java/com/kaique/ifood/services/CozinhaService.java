package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

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

	public Optional<Cozinha> buscaPorId(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Cozinha adiciona(Cozinha Cozinha) {
		return repository.save(Cozinha);
	}

	@Transactional
	public Cozinha atualiza(Long id, Cozinha NovaCozinha) {

		if (repository.findById(id).isEmpty())
			throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));

		Cozinha CozinhaAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovaCozinha, CozinhaAtual, "id");
		return repository.save(CozinhaAtual);
	}

	@Transactional
	public void deletar(Long id) {

		if (repository.findById(id).isEmpty())
			throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado", id));

		repository.deleteById(id);

	}
}