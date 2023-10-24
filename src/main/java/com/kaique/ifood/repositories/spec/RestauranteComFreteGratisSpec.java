package com.kaique.ifood.repositories.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.kaique.ifood.entities.Restaurante;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RestauranteComFreteGratisSpec implements Specification<Restaurante> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}
