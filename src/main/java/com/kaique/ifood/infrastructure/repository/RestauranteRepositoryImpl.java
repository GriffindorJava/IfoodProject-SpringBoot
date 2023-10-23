package com.kaique.ifood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaique.ifood.entities.Restaurante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql = "SELECT r FROM Restaurante r WHERE r.nome LIKE :pesquisa AND r.taxaFrete BETWEEN :taxa_inicial AND :taxa_final";

		return manager.createQuery(jpql, Restaurante.class).setParameter("pesquisa", "%" + nome + "%")
				.setParameter("taxa_inicial", taxaFreteInicial).setParameter("taxa_final", taxaFreteFinal)
				.getResultList();
	}
}
