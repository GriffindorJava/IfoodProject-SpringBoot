package com.kaique.ifood.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.kaique.ifood.entities.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha , Long>{

	List<Cozinha>  findByNomeContainsIgnoreCase( String nome);
}
