package com.kaique.ifood.mixin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.entities.Endereco;
import com.kaique.ifood.entities.FormaPagamento;
import com.kaique.ifood.entities.Produto;

public class RestauranteMixin {

	@JsonIgnore
	private LocalDateTime dataCadastro;

	@JsonIgnore
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnoreProperties(value = "nome" ,allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();

	@JsonIgnore
	private List<FormaPagamento> formaPagamentos = new ArrayList<>();

}
