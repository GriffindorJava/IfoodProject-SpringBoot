package com.kaique.ifood.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.kaique.ifood.entities.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestaurantesRepositoryQueries , JpaSpecificationExecutor<Restaurante>{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	/*
	 * nessa consulta agora esta em um arquivo xlm na pasta META-INF para treino e
	 * organização
	 */
	//@Query("SELECT r FROM Restaurante r WHERE r.nome LIKE %:nome% AND r.cozinha.id = :id")
	List<Restaurante> consultarPorNome(@Param("nome") String nome, @Param("id") BigDecimal id);

	/*
	 * esse método tem como intuído fazer a mesma busca que o de cima mais sem a
	 * necessidade de usar o @Query
	 */

	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, BigDecimal
	// id);
}
