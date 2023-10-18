package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.CozinhaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

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
	public Cozinha adiciona(Cozinha estado) throws ConstraintViolationException {
		return repository.save(estado);
	}

	@Transactional
	public Cozinha atualiza(Long id, Cozinha NovoCozinha) throws ConstraintViolationException {
		Cozinha estadoAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoCozinha, estadoAtual, "id");
		return repository.save(estadoAtual);
	}


    @Transactional
    public void deletar(Long id) {
    	
        try {
            if (repository.findById(id).isEmpty()) 
                throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado", id));
            repository.deleteById(id);
          
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
        }
    }
}