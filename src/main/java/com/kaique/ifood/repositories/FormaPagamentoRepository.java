package com.kaique.ifood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.ifood.entities.Restaurante;

public interface FormaPagamentoRepository extends JpaRepository<Restaurante, Long>{

}
