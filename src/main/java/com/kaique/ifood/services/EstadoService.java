package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.repositories.EstadoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> listar() {
		return repository.findAll();
	}

	public Optional<Estado> buscaPorId(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Estado adiciona(Estado estado) throws ConstraintViolationException {
		return repository.save(estado);
	}

	@Transactional
	public Estado atualiza(Long id, Estado NovoEstado) throws ConstraintViolationException {
		Estado estadoAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoEstado, estadoAtual, "id");
		return repository.save(estadoAtual);
	}
}
