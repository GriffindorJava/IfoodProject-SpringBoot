package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.RestauranteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository repository;

	public List<Restaurante> listar() {
		return repository.findAll();
	}

	public Optional<Restaurante> buscaPorId(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Restaurante adiciona(Restaurante restaurante) throws ConstraintViolationException {
		return repository.save(restaurante);
	}

	@Transactional
	public Restaurante atualiza(Long id, Restaurante NovoRestaurante) throws ConstraintViolationException {
		Restaurante restauranteAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoRestaurante, restauranteAtual, "id");
		return repository.save(restauranteAtual);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			if (repository.findById(id).isEmpty())
				throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removido , pois está em uso ", id));
		}

	}
}
