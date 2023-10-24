package com.kaique.ifood.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.RestauranteRepository;

import jakarta.transaction.Transactional;

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

	public List<Restaurante> filtraPorTaxas(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return repository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> buscaPorNomeEIdDeCozinha(String nome, BigDecimal id) {
		return repository.consultarPorNome(nome, id);
	}

	public List<Restaurante> buscaRTTPorNomeFrete(String nome, BigDecimal taxaFreteInicia, BigDecimal taxaFreteFinal) {
		return repository.find(nome, taxaFreteInicia, taxaFreteFinal);
	}

	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return repository.findComFreteGratis(nome);
	}

	@Transactional
	public Restaurante adiciona(Restaurante restaurante) {
		return repository.save(restaurante);
	}

	@Transactional
	public Restaurante atualiza(Long id, Restaurante NovoRestaurante) {
		if (repository.findById(id).isEmpty())
			throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));

		Restaurante restauranteAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoRestaurante, restauranteAtual, "id");
		return repository.save(restauranteAtual);
	}

	@Transactional
	public void deletar(Long id) {

		if (repository.findById(id).isEmpty())
			throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));
		repository.deleteById(id);
	}
}
