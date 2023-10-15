package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.repositories.EstadoRepository;

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
}
