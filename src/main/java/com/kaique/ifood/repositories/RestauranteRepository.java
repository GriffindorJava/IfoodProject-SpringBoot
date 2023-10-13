package com.kaique.ifood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.ifood.entities.Cozinha;

public interface RestauranteRepository extends JpaRepository<Cozinha, Long>{

}
