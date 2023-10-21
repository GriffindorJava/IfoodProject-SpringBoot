package com.kaique.ifood.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaique.ifood.entities.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	/*
	 * nesse método os argumentos são passados com o mesmo nome das variáveis
	 * inibindo a necessidade de usar a anotação @param("ex")
	 */
	@Query("SELECT r FROM Restaurante r WHERE r.nome LIKE %:nome% AND r.cozinha.id = :id")
	List<Restaurante> consultaCozinha(@Param("nome") String nome, @Param("id") BigDecimal id);


	/*
	 * esse método tem como intuído fazer a mesma busca que o de cima mais sem a
	 * necessidade de usar o @Query
	 */
	
	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, BigDecimal id);
}
