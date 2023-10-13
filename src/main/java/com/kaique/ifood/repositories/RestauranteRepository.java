package com.kaique.ifood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.ifood.entities.FormaPagamento;

public interface RestauranteRepository extends JpaRepository<FormaPagamento, Long>{

}
