package com.kaique.ifood.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> listar() {
		return repository.findAll();
	}

	public Estado buscaPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id)));
	}

	@Transactional
	public Estado adiciona(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public Estado atualiza(Long id, Estado NovoEstado) {
		if (repository.findById(id).isEmpty())
			throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));

		Estado estadoAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoEstado, estadoAtual, "id");
		return repository.save(estadoAtual);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			if (repository.findById(id).isEmpty())
				throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));
			
			repository.deleteById(id);
			repository.flush();
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Código %d não pode ser apagado pós esta em uso", id));
		}

	}
}
