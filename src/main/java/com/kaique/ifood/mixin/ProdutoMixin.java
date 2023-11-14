package com.kaique.ifood.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaique.ifood.entities.Restaurante;

public class ProdutoMixin {

	@JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Restaurante restaurante;
}
