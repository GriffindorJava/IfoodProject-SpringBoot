package com.kaique.ifood.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.ifood.entities.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial , BigDecimal taxaFinal);
}
