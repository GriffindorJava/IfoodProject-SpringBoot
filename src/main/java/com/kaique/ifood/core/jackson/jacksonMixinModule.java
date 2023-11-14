package com.kaique.ifood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaique.ifood.entities.Cozinha;
import com.kaique.ifood.entities.Endereco;
import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.entities.Produto;
import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.mixin.CozinhaMixin;
import com.kaique.ifood.mixin.EnderecoMixin;
import com.kaique.ifood.mixin.EstadoMixin;
import com.kaique.ifood.mixin.ProdutoMixin;
import com.kaique.ifood.mixin.RestauranteMixin;

@Component
public class jacksonMixinModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	public jacksonMixinModule() {
		
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		
		setMixInAnnotation(Endereco.class, EnderecoMixin.class);
		
		setMixInAnnotation(Estado.class, EstadoMixin.class);
		
		setMixInAnnotation(Produto.class, ProdutoMixin.class);
	}
}
