package com.kaique.ifood.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EstadoNaoEncontradaException;
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
		return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradaException(id));
	}

	@Transactional
	public Estado adiciona(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public Estado atualiza(Long id, Estado NovoEstado) {

		Estado estadoAtual = buscaPorId(id);
		BeanUtils.copyProperties(NovoEstado, estadoAtual, "id");
		return repository.save(estadoAtual);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			buscaPorId(id);
			repository.deleteById(id);
			repository.flush();

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(id);
		}

	}
}
