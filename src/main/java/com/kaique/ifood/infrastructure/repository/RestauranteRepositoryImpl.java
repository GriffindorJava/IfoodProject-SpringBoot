package com.kaique.ifood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaique.ifood.entities.Restaurante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
				CriteriaBuilder bulder = manager.getCriteriaBuilder();
				
				CriteriaQuery<Restaurante> criterio = bulder.createQuery(Restaurante.class);
			    Root<Restaurante> root = criterio.from(Restaurante.class);
				
			    Predicate nomePredicate = bulder.like(root.get("nome"),"%" + nome + "%");
			    
			    Predicate taxaInicialPredicate = bulder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
			    
			    Predicate taxaFinalPredicate = bulder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
			    
			    criterio.where(nomePredicate , taxaInicialPredicate , taxaFinalPredicate);
			    
				TypedQuery<Restaurante> query = manager.createQuery(criterio);
				return query.getResultList();
	}
}
