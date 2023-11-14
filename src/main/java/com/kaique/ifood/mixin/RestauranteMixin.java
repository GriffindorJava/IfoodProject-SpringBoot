package com.kaique.ifood.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.entities.Endereco;
import com.kaique.ifood.entities.FormaPagamento;
import com.kaique.ifood.entities.Produto;

public class RestauranteMixin {

	//@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime dataCadastro;

	//
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
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
