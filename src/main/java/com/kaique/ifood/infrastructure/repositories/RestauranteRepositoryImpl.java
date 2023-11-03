package com.kaique.ifood.infrastructure.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.repositories.RestauranteRepository;
import com.kaique.ifood.repositories.RestaurantesRepositoryQueries;
import com.kaique.ifood.repositories.spec.RestauranteSpecs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements RestaurantesRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder bulder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criterio = bulder.createQuery(Restaurante.class);
		Root<Restaurante> root = criterio.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(nome)) {
			predicates.add(bulder.like(root.get("nome"), "%" + nome + "%"));
		}

		if (taxaFreteInicial != null) {
			predicates.add(bulder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		if (taxaFreteFinal != null) {
			predicates.add(bulder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}

		criterio.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criterio);
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository
				.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}
}
