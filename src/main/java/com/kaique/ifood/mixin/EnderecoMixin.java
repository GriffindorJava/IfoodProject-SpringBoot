package com.kaique.ifood.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaique.ifood.entities.Cidade;

public class EnderecoMixin {

	@JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Cidade cidade;
}
