package com.kaique.ifood.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.kaique.ifood.entities.Restaurante;

public interface RestaurantesRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	List<Restaurante> findComFreteGratis(String nome);
}