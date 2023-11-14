package com.kaique.ifood.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaique.ifood.entities.Cidade;

public class EstadoMixin {

	@JsonIgnore
	private List<Cidade> cidades = new ArrayList<>();
}
