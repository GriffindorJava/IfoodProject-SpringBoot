package com.kaique.ifood.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.repositories.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository repository;
	
	public List<Cozinha> listar() {
		return repository.findAll();
	}
}
