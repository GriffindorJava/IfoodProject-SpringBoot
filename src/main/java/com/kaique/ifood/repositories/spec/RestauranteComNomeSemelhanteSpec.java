package com.kaique.ifood.repositories.spec;

import org.springframework.data.jpa.domain.Specification;

import com.kaique.ifood.entities.Restaurante;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteComNomeSemelhanteSpec implements Specification<Restaurante> {
	private static final long serialVersionUID = 1L;

	private String nome;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder Builder) {

		return Builder.like(root.get("nome"), "%" + nome + "%");
	}

}
